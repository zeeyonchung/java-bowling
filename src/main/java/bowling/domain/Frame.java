package bowling.domain;

import java.util.List;

public interface Frame {
    boolean addPinCount(int nextPinCount);

    int getScore();

    boolean isDone();

    List<PinCount> getPinCounts();

    Frame createNext();

    Frame getNext();

    boolean isLast();
}