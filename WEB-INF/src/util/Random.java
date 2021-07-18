package util;

/**
 * 線形合同法による乱数生成
 */
public class Random {
    private int S = 1;
    private int A = 1664525;
    private int B = 1013904223;
    private int C = 2147483647;

    /**
     * コンストラクタ
     * シード値を設定
     */
    public Random(){
        setSeed();
    }
    /**
     * シード値を設定
     */
    public void setSeed() {
        // ミリ秒を取得
        long startTime = System.currentTimeMillis();
        int s = (int) (startTime % 100);
        S = s;
    }
    /**
     * 乱数生成
     * @return 乱数
     */
    public int rand() {
        S = ((S * A + B) &  C) % 100;
        return S;
    }
}
