package beans;

public class Fortune {

    private String result;
    private String text;
    private String img;
    private int probability;
    private int id;
    private String timezoneName;

	/**
	 * コンストラクタ
	 * @param result おみくじ結果
	 * @param text おみくじ一言
	 * @param img おみくじ画像
	 * @param probability おみくじ確率
	 */
    public Fortune(String result, String text, String img, int probability, int id) {
        setResult(result);
        setText(text);
        setImg(img);
        setProbability(probability);
        setId(id);
    }

    public Fortune(String result, String text, String img, int probability, String timezoneName, int id) {
        setResult(result);
        setText(text);
        setImg(img);
        setProbability(probability);
        setTimeZoneName(timezoneName);
        setId(id);
    }

    /**
     * デフォルトコンストラクタ
     */
    public Fortune() {

    }

    public String getResult() {
        return result;
    }
    public String getText() {
        return text;
    }
    public String getImg() {
        return img;
    }
    public int getProbability() {
        return probability;
    }
    public int getId() {
        return id;
    }
    public String getTimeZoneName() {
        return timezoneName;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public void setProbability(int probability) {
        this.probability = probability;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setTimeZoneName(String timezoneName) {
        this.timezoneName = timezoneName;
    }
}
