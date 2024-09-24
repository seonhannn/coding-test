package programmers;

// https://school.programmers.co.kr/learn/courses/30/lessons/340213

// test
class Test {

    public static void main(String[] args) {
        String video_len = "02:05";
        String pos = "01:58";
        String op_start = "00:10";
        String op_end = "00:20";
        String[] commands = {"next", "prev", "next"};

        Solution s = new Solution();

        s.solution(video_len, pos, op_start, op_end, commands);

        System.out.println(s.solution(video_len, pos, op_start, op_end, commands));
    }
}

// solution
class Solution {

    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        int currentTime = timeToSeconds(pos); // 초 단위로 변환
        int videoLength = timeToSeconds(video_len); // 동영상 길이도 초 단위로 변환
        int opStartTime = timeToSeconds(op_start); // 오프닝 시작 시간
        int opEndTime = timeToSeconds(op_end); // 오프닝 끝 시간

        // 오프닝 건너뛰기
        currentTime = skipOp(opStartTime, opEndTime, currentTime);

        for (String command : commands) {
            if (command.equals("prev")) {
                currentTime = prev(currentTime);
            } else if (command.equals("next")) {
                currentTime = next(currentTime, videoLength);
            }
            // prev, next 후 오프닝 건너뛰기
            currentTime = skipOp(opStartTime, opEndTime, currentTime);
        }

        // 최종 시간 mm:ss 형식으로 변환하여 반환
        return secondsToTime(currentTime);
    }

    int prev(int currentTime) {
        // 현재 위치가 10초 미만이면 0으로 이동, 아니면 10초 뒤로
        return Math.max(0, currentTime - 10);
    }

    int next(int currentTime, int videoLength) {
        // 남은 시간이 10초 미만이면 마지막으로 이동, 아니면 10초 앞으로
        return Math.min(videoLength, currentTime + 10);
    }

    int skipOp(int opStartTime, int opEndTime, int currentTime) {
        // 오프닝 구간에 있으면 오프닝 끝나는 시각으로 이동
        if (currentTime >= opStartTime && currentTime <= opEndTime) {
            return opEndTime;
        }
        return currentTime;
    }

    int timeToSeconds(String time) {
        // mm:ss 형식을 초 단위로 변환
        String[] parts = time.split(":");
        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);
        return minutes * 60 + seconds;
    }

    String secondsToTime(int totalSeconds) {
        // 초 단위를 mm:ss 형식으로 변환
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
