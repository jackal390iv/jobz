/*
 * changed
 */
package edu.frostburg.butler.jon.application.logic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.SelectableChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * changed
 */
public class ApplicationLogicProcessor {

    private static final Logger LOG
            = Logger.getLogger(ApplicationLogicProcessor.class.getName());

    Collection<ApplicationLogicComponent> alcs;

    public ApplicationLogicProcessor() {
        this.alcs = new ArrayList<>();
    }

    public int size() {
        return alcs.size();
    }

    public boolean isEmpty() {
        return alcs.isEmpty();
    }

    public boolean contains(Object o) {
        return alcs.contains(o);
    }

    public Iterator<ApplicationLogicComponent> iterator() {
        return alcs.iterator();
    }

    public Object[] toArray() {
        return alcs.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return alcs.toArray(a);
    }

    public boolean add(ApplicationLogicComponent e) {
        return alcs.add(e);
    }

    public boolean remove(Object o) {
        return alcs.remove(o);
    }

    public boolean containsAll(
            Collection<?> c) {
        return alcs.containsAll(c);
    }

    public boolean addAll(
            Collection<? extends ApplicationLogicComponent> c) {
        return alcs.addAll(c);
    }

    public boolean removeAll(
            Collection<?> c) {
        return alcs.removeAll(c);
    }

    public boolean removeIf(
            Predicate<? super ApplicationLogicComponent> filter) {
        return alcs.removeIf(filter);
    }

    public boolean retainAll(
            Collection<?> c) {
        return alcs.retainAll(c);
    }

    public void clear() {
        alcs.clear();
    }

    public Spliterator<ApplicationLogicComponent> spliterator() {
        return alcs.spliterator();
    }

    public Stream<ApplicationLogicComponent> stream() {
        return alcs.stream();
    }

    public Stream<ApplicationLogicComponent> parallelStream() {
        return alcs.parallelStream();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.alcs);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ApplicationLogicProcessor other = (ApplicationLogicProcessor) obj;
        if (!Objects.equals(this.alcs, other.alcs)) {
            return false;
        }
        return true;
    }

    public void processApplicationLogic() {
        this.alcs.parallelStream()
                .forEach((applicationLogicComponent)
                        -> {
            LOG.log(Level.INFO, "Running Application Logic Component \"{0}\"",
                    applicationLogicComponent.getDescriptor()
                    .getDescriptionString());
            applicationLogicComponent.run();
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ApplicationLogicProcessor applicationLogicProcessor
                = new ApplicationLogicProcessor();
        applicationLogicProcessor.addAll(Arrays.asList(
                new ApplicationLogicComponent() {
                    final ApplicationLogicConsoleLogger wb
                            = new ApplicationLogicConsoleLogger();

                    @Override
                    public Description getDescriptor() {
                        return new Description("Prints Hello World");
                    }

                    @Override
                    public void run() {
                        final ByteBuffer bb = ByteBuffer.wrap("Hello World\n"
                                .getBytes());

                        while (bb.remaining() > 0) {
                            try {
                                wb.write(bb);
                            } catch (IOException ex) {
                                Logger.getLogger(ApplicationLogicProcessor.class
                                        .getName())
                                .log(Level.SEVERE, null, ex);
                            }
                        }

                    }
                }, new ApplicationLogicComponent() {

                    final ApplicationLogicConsoleLogger wb
                    = new ApplicationLogicConsoleLogger();

                    @Override
                    public Description getDescriptor() {
                        return new Description("Do math");
                    }

                    @Override
                    public void run() {
                        ByteArrayOutputStream bao = new ByteArrayOutputStream();
                        IntStream.range(0, 100)
                        .map((i) -> IntStream.range(0, i)
                                .sum())
                        .forEach((sum) -> {
                    try {
                        bao.write(Integer.toString(sum)
                                .getBytes());
                    } catch (IOException ex) {
                        Logger.getLogger(ApplicationLogicProcessor.class
                                .getName())
                                .log(Level.SEVERE, null, ex);
                    }
                    final ByteBuffer bb = ByteBuffer.wrap(bao.toByteArray());
                    while (bb.remaining() > 0) {
                        try {
                            wb.write(bb);
                        } catch (IOException ex) {
                            Logger.getLogger(ApplicationLogicProcessor.class
                                    .getName())
                                    .log(Level.SEVERE, null, ex);
                        }
                    }
                });
                    }
                }));
        applicationLogicProcessor.processApplicationLogic();
    }

    private static class ApplicationLogicConsoleLogger {

        private final WritableByteChannel wb = Channels.newChannel(
                System.out);

        public int write(ByteBuffer src) throws IOException {
            return wb.write(src);
        }

        public boolean isOpen() {
            return wb.isOpen();
        }

        public void close() throws IOException {
            wb.close();
        }

    }
}
