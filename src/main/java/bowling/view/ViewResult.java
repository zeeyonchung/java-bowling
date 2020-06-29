package bowling.view;

import bowling.domain.score.Result;
import bowling.domain.score.Score;
import bowling.domain.score.FrameScore;

import java.util.Arrays;
import java.util.Optional;

public enum ViewResult {
    STRIKE (Result.STRIKE) {
        @Override
        String parseScore(Score first, Score second) {
            return "   X    ";
        }
    },
    SPARE (Result.SPARE) {
        @Override
        String parseScore(Score first, Score second) {
            return String.format("  %d|/   ", first.getContent());
        }
    },
    MISS (Result.MISS) {
        @Override
        String parseScore(Score first, Score second) {
            return String.format("   %d|%d  ", first.getContent(), second.getContent());
        }
    },
    GUTTER (Result.GUTTER) {
        @Override
        String parseScore(Score first, Score second) {
            return "   -    ";
        }
    };

    private final Result result;

    ViewResult(Result result) {
        this.result = result;
    }

    private static ViewResult findByResult(Result result) {
        return Arrays.stream(values())
                .filter(viewResult -> viewResult.result == result)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("프레임 결과에 해당하는 ViewResult가 없습니다"));
    }

    public static String parseFrameScore(FrameScore frameScore) {
        Optional<Score> first = frameScore.getFirst();
        Optional<Score> second = frameScore.getSecond();

        if (!first.isPresent()) {
            return "        ";
        }

        return second
                .map(secondScore -> parseFirstAndSecond(frameScore.checkResult(), first.get(), secondScore))
                .map(string -> string + parseBonus(frameScore))
                .orElseGet(() -> String.format("   %s    ", first.get().getContent()));
    }

    private static String parseFirstAndSecond(Result result, Score first, Score second) {
        ViewResult viewResult = findByResult(result);
        return viewResult.parseScore(first, second);
    }

    private static String parseBonus(FrameScore frameScore) {
        return frameScore.getBonus()
                .map(bonus -> String.format(" + %d ", bonus.getContent()))
                .orElse("");
    }

    abstract String parseScore(Score first, Score second);
}
