import processing.core.PApplet;

public class Main extends PApplet {

    static String graphFramePath = "/window_frames/SciFiFrame.png";
    static String graphBackgroundPath = "/window_backgrounds/AbstractCircuit.jpg";

    static BarGraphWindow meatGraph;
    static BarGraphWindow HPGraph;

    static float[] meatDataSet = {
            12,
            800,
            1200,
            0
    };

    static String[] meatDataLabels = {
            "Cow",
            "Lab",
            "Xeno Purge",
            "Terravore"
    };

    static float[] HPDataSet = {
            10,
            5,
            15,
            60,
            30,
            25,
            35
    };

    static String[] HPDataLabels = {
            "Human",
            "Goblin",
            "Orc",
            "Dragon",
            "Troll",
            "Eladrin",
            "Giant"
    };

    public void settings() {
        size(400, 400);
    }

    public void setup() {
        background(255);
    }

    public static void main(String[] args) {

        meatGraph = new BarGraphWindow()
                .setData(meatDataSet)
                .setLabels(meatDataLabels)
                .setShowXAxis()
                .setShowYAxis()
                .setYScale(0.6f)
                .setTextSize(16)
                .setTextColor(255, 255, 255, 255)
                .setTitle("LBs of Meat / Sample Type")
                .setTitleSize(24)
                .setXMargin(320)
                .setYBottomMargin(200)
                .setYTopMargin(100)
                .setBarWidth(100)
                .setBarSpacing(100)
                .setBarColor(60, 60, 255, 220)
                .setBackgroundImageLocal(graphBackgroundPath)
                .setFrameImageLocal(graphFramePath)
                .autoSize()
                .init();

        HPGraph = new BarGraphWindow()
                .setData(HPDataSet)
                .setLabels(HPDataLabels)
                .setTitle("HP of Various Races")
                .setXMargin(50)
                .setYTopMargin(30)
                .setYBottomMargin(30)
                .setBarSpacing(20)
                .setBarWidth(30)
                .setYScale(5)
                .setShowYAxis()
                .setShowXAxis()
                .setBarColor(255, 0, 0, 255)
                .autoSize()
                .init();

        PApplet.main("Main");

    }

    public void draw() {

    }

    @Override
    public void keyPressed() {
        if(key == 'm') {
            if (meatGraph.active()) {
                meatGraph.hide();
            } else {
                meatGraph.show();
            }
        }else if(key == 'h') {
            if (HPGraph.active()) {
                HPGraph.hide();
            } else {
                HPGraph.show();
            }
        }
    }

}
