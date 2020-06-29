package bowling.domain.frame;

import bowling.domain.score.Score;
import bowling.domain.score.FrameScore;

public interface Frame {
    Frame createNext(boolean isNextLast);

    boolean canAddMoreScore();

    void addScore(Score score);

    FrameScore getFrameScore();

    int getIndex();
}
