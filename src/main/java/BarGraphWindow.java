import processing.core.PApplet;
import processing.core.PImage;

public class BarGraphWindow extends PApplet {

    //-----------------------------------------------------------------------------------------------------------------
    // State

    private int sizeX = 100, sizeY = 100;

    private float[] data = {};
    private String[] labels = {};
    private String title = null;

    private int textSize = 12;
    private int titleSize = 16;
    private int barSpacing = 0;
    private int barWidth = 10;
    private float YScale = 10;
    private int XMargin = 5;
    private int YTopMargin = 5;
    private int YBottomMargin = 0;

    private int[] backgroundColor = {255, 255, 255, 255};
    private int[] barColor = {255, 255, 255, 255};
    private int[] textColor = {0, 0, 0, 255};

    private boolean showXAxis = false;
    private boolean showYAxis = false;

    private boolean autosize = false;

    private boolean active = false;

    private String frameImagePath = null;
    private String frameImageExtension = null;
    private PImage frameImage = null;

    private String backgroundImagePath = null;
    private String backgroundImageExtension = null;
    private PImage backgroundImage = null;

    //-----------------------------------------------------------------------------------------------------------------
    // Constructors

    public BarGraphWindow() {
        super();
    }

    public BarGraphWindow(int sizeX, int sizeY) {
        super();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Initialization function
    // Once this is called, modifications can no longer be made to the state via the below setter functions
    // This runs a threaded PApplet sketch, do not call it more than once if you know what's good for you

    public BarGraphWindow init() {
        PApplet.runSketch(new String[] {this.getClass().getSimpleName()}, this);
        surface.setVisible(false);
        return this;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Visibility functions

    public void show() {
        active = true;
        surface.setVisible(true);
    }

    public void hide() {
        active = false;
        surface.setVisible(false);
    }

    public boolean active() {
        return this.active;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // These are all of the setters for the window
    // These are used to modify internal state
    // State cannot be modified after the window is initialized
    // Setters return this instance to allow for chaining on initialization
    // Setters can be called in any order, except that autoSize() should be called last if it is to be used

    public BarGraphWindow setData(float[] data) {
        this.data = data;
        return this;
    }

    public BarGraphWindow setLabels(String[] labels) {
        this.labels = labels;
        return this;
    }

    public BarGraphWindow setTitle(String title) {
        this.title = title;
        return this;
    }

    public BarGraphWindow setTextSize(int size) {
        textSize = size;
        return this;
    }

    public BarGraphWindow setTitleSize(int size) {
        titleSize = size;
        return this;
    }

    public BarGraphWindow setTextColor(int r, int g, int b, int a) {
        textColor = new int[] {r, g, b, a};
        return this;
    }

    public BarGraphWindow setShowXAxis() {
        showXAxis = true;
        return this;
    }

    public BarGraphWindow setShowYAxis() {
        showYAxis = true;
        return this;
    }

    public BarGraphWindow setBarSpacing(int spacing) {
        this.barSpacing = spacing;
        return this;
    }

    public BarGraphWindow setBarWidth(int width) {
        this.barWidth = width;
        return this;
    }

    public BarGraphWindow setXMargin(int margin) {
        this.XMargin = margin;
        return this;
    }

    public BarGraphWindow setYTopMargin(int margin) {
        this.YTopMargin = margin;
        return this;
    }

    public BarGraphWindow setYBottomMargin(int margin) {
        this.YBottomMargin = margin;
        return this;
    }

    public BarGraphWindow setYScale(float scale) {
        this.YScale = scale;
        return this;
    }

    public BarGraphWindow setBackgroundColor(int r, int g, int b, int a) {
        backgroundColor = new int[] {r, g, b, a};
        return this;
    }

    public BarGraphWindow setBarColor(int r, int g, int b, int a) {
        barColor = new int[] {r, g, b, a};
        return this;
    }

    public BarGraphWindow setFrameImageLocal(String path) {
        this.frameImagePath = path;
        return this;
    }

    public BarGraphWindow setFrameImageURL(String url, String extension) {
        this.frameImagePath = url;
        this.frameImageExtension = extension;
        return this;
    }

    public BarGraphWindow setBackgroundImageLocal(String path) {
        this.backgroundImagePath = path;
        return this;
    }

    public BarGraphWindow setBackgroundImageURL(String url, String extension) {
        this.backgroundImagePath = url;
        this.backgroundImageExtension = extension;
        return this;
    }

    // Should always be called last when used
    public BarGraphWindow autoSize() {
        this.autosize = true;

        sizeX = (XMargin * 2) + (barWidth * data.length) + (barSpacing * (data.length - 1));
        sizeY = (int)(YTopMargin + YBottomMargin + (dataMax() * YScale));

        return this;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // This is the initialization order for this class
    // These are called AFTER all other setters, in the order specified
    // Be mindful of the order these are called when modifying initialization behavior
    // (because null logic is used, crashes can occur if state initialization happens out of order)

    public void settings() { // Called FIRST, after init()
        size(sizeX, sizeY);
    }

    public void setup() { // Called SECOND, after settings()
        if(frameImagePath != null && frameImageExtension == null)
            frameImage = loadImage(frameImagePath);
        else if(frameImagePath != null) //frameImageExtension != null will always be true when this branch if reached
            frameImage = loadImage(frameImagePath, frameImageExtension);

        if(backgroundImagePath != null && backgroundImageExtension == null)
            backgroundImage = loadImage(backgroundImagePath);
        else if(backgroundImagePath != null) //backgroundImageExtension != null will always be true when this branch if reached
            backgroundImage = loadImage(backgroundImagePath, backgroundImageExtension);

        if(autosize)
            postAutoSize();
    }

    private void postAutoSize() { // Called THIRD, after setup, if applicable
        if(frameImage != null) {
            frameImage.resize(sizeX, sizeY);
        }

        if(backgroundImage != null) {
            backgroundImage.resize(sizeX, sizeY);
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // This is where the window is drawn
    // All modifications to rendering and window appearance should happen here
    // Keep in mind that the order of rendering determines "layers"
    // For example, drawing the background last will draw it over the graph, making the graph invisible

    public void draw() {

        //draw background
        if(backgroundImage == null)
            g.background(backgroundColor[0], backgroundColor[1], backgroundColor[2], backgroundColor[3]);
        else
            g.background(backgroundImage);

        //draw title
        if(title != null) {
            textAlign(CENTER);
            textSize(titleSize);
            g.text(title, width / 2, YTopMargin / 2);
        }

        //draw data
        for (int i = 0; i < data.length; i++) {

            int x = (i * barWidth) + (i * barSpacing) + XMargin;
            int y = height - YBottomMargin;

            g.fill(barColor[0], barColor[1], barColor[2], barColor[3]);
            g.rect(x, y, barWidth, -data[i] * YScale);
        }

        //draw axes and labels
        if(showXAxis) {
            line(XMargin, height - YBottomMargin, width - XMargin, height - YBottomMargin);
            if (labels.length > 0) {
                for(int i = 0; i < labels.length; i++) {
                    int x = (i * barWidth) + (i * barSpacing) + XMargin;
                    int y = height - YBottomMargin;

                    textAlign(CENTER);
                    textSize(textSize);
                    g.fill(textColor[0], textColor[1], textColor[2], textColor[3]);
                    g.text(labels[i], x + (barWidth / 2), y + textSize + 5);
                }
            }
        }

        if(showYAxis) {
            line(XMargin, height - YBottomMargin, XMargin, YTopMargin);

            for(int i = 0; i <= 4; i++) {

                int yDiff = (height - YBottomMargin) - YTopMargin;
                int y = (int)(yDiff * (float)i / 4f) + YTopMargin;

                textAlign(CENTER);
                textSize(textSize);
                g.text(String.format("%.1f", dataMax() * (1f -  (float)i / 4f)), XMargin - (textSize * 3), y);
            }

        }

        //draw frame overlay
        if(frameImage != null) {
            imageMode(CORNER);
            g.image(frameImage, 0, 0, width, height);
        }

    }

    //-----------------------------------------------------------------------------------------------------------------
    // Utility functions for internal math

    private float dataMax() {
        float max = 0;
        for(float datum: data) {
            if(datum > max)
                max = datum;
        }
        return max;
    }

}
