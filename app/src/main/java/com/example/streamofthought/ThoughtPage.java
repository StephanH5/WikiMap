package com.example.streamofthought;

import java.net.URL;

public class ThoughtPage {
    ThoughtPage left = null;
    ThoughtPage down = null;
    ThoughtPage up = null;
    ThoughtPage right = null;

    String url;

    ThoughtPage(String str) {url = str;}

    public String getUrl() {
        return url;
    }

    public void addRelation(String dir, String str)
    {
        switch(dir){
            case "left" :
                left = new ThoughtPage(str);
                left.right = this;
                break;
            case "down" :
                down = new ThoughtPage(str);
                down.up = this;
                break;
            case "up" :
                up = new ThoughtPage(str);
                up.down = this;
                break;
            case "right" :
                right = new ThoughtPage(str);
                right.left = this;
                break;
        }
    }

    public void deleteRelation(String str)
    {
        boolean leftb = true;
        boolean downb = true;
        boolean upb = true;
        boolean rightb = true;

        switch(str)  {
            //coming from the right neighbor, don't delete right neighbor
            case "left" :
                rightb = false;
                break;
            case "down" :
                upb = false;
                break;
            case "up" :
                downb = false;
                break;
            case "right" :
                leftb = false;
                break;
            default:
                leftb = false;
                downb = false;
                upb = false;
                rightb = false;
                break;
        }

        if(left != null || leftb)
        {
            left.deleteRelation("left");
            left = null;
        }
        if(down != null || downb)
        {
            down.deleteRelation("down");
            down = null;
        }
        if(up != null || upb)
        {
            up.deleteRelation("up");
            up = null;
        }
        if(right != null || rightb)
        {
            right.deleteRelation("right");
            right = null;
        }

    }
}
