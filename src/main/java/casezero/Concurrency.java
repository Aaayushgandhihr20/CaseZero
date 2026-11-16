package casezero;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

// Stores one activity performed during the investigation
class GameEvent {

    private String message;
    private LocalDateTime time;

    public GameEvent(String message) {
        this.message = message;
        this.time = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return time + " - " + message;
    }
}

// Safely stores events until the background logger processes them
class ActivityQueue {

    private Queue<GameEvent> events = new LinkedList<>();
    private final int capacity = 10;
    private boolean closed = false;

    public synchronized void addEvent(GameEvent event)
            throws InterruptedException {

        while (events.size() >= capacity && !closed) {
            wait();
        }

        if (closed) {
            return;
        }

        events.add(event);
        notifyAll();
    }

    public synchronized GameEvent getEvent()
            throws InterruptedException {

        while (events.isEmpty() && !closed) {
            wait();
        }

        if (events.isEmpty()) {
            return null;
        }

        GameEvent event = events.poll();
        notifyAll();

        return event;
    }

    public synchronized void close() {
        closed = true;
        notifyAll();
    }

    public synchronized boolean isClosed() {
        return closed;
    }
}

// Runs in the background and writes events into a log file
class ActivityLogger implements Runnable {

    private ActivityQueue queue;
    private Path logFile = Path.of("logs", "activity.log");

    public ActivityLogger(ActivityQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        boolean interrupted = false;

        while (true) {

            GameEvent event;

            try {
                event = queue.getEvent();
            } catch (InterruptedException e) {
                interrupted = true;
                continue;
            }

            if (event == null) {
                break;
            }

            writeEvent(event);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }

        if (interrupted) {
            Thread.currentThread().interrupt();
        }
    }

    private void writeEvent(GameEvent event) {

        try {

            Files.createDirectories(logFile.getParent());

            try (BufferedWriter writer = Files.newBufferedWriter(
                    logFile,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND)) {

                writer.write(event.toString());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Could not write activity log: "
                    + e.getMessage());
        }
    }
}
