package safebox.yiye.com.safebox.beans;

public class IndexLocationCarEPChildItemBean {
    private String title;//子项显示的文字
    private int markerImgId;//每个子项的图标

    public IndexLocationCarEPChildItemBean(String title, int markerImgId) {
        this.title = title;
        this.markerImgId = markerImgId;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMarkerImgId() {
        return markerImgId;
    }

    public void setMarkerImgId(int markerImgId) {
        this.markerImgId = markerImgId;
    }


}