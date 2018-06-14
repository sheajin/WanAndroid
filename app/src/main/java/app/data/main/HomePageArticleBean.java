package app.data.main;

import java.util.List;

/**
 * Created by jxy on 2018/6/8.
 */

public class HomePageArticleBean {

    /**
     * curPage : 3
     * datas : [{"apkLink":"","author":"wangshub","chapterId":363,"chapterName":"创意汇","collect":false,"courseId":13,"desc":"Python 抖音机器人，论如何在抖音上找到漂亮小姐姐？\r\n","envelopePic":"http://www.wanandroid.com/blogimgs/2316d9df-158d-4afb-9e15-39ca36e428ff.png","fresh":false,"id":2957,"link":"http://www.wanandroid.com/blog/show/2142","niceDate":"2018-05-29","origin":"","projectLink":"https://github.com/wangshub/Douyin-Bot","publishTime":1527570400000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=363"}],"title":"Python 抖音机器人 Douyin-Bot","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"小编","chapterId":274,"chapterName":"个人博客","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2956,"link":"http://prototypez.github.io/","niceDate":"2018-05-29","origin":"","projectLink":"","publishTime":1527559418000,"superChapterId":272,"superChapterName":"导航主Tab","tags":[{"name":"导航","url":"/navi#274"}],"title":"prototypez","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"电点mxn","chapterId":249,"chapterName":"干货资源","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2955,"link":"https://www.jianshu.com/p/be12524418a4","niceDate":"2018-05-29","origin":"","projectLink":"","publishTime":1527558651000,"superChapterId":249,"superChapterName":"干货资源","tags":[],"title":"勇敢跨越，从0到1开发一个属于自己的App","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"albon","chapterId":362,"chapterName":"泛型","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2954,"link":"https://www.jianshu.com/p/ca12115bf92c","niceDate":"2018-05-28","origin":"","projectLink":"","publishTime":1527508459000,"superChapterId":245,"superChapterName":"Java深入","tags":[],"title":"深入理解 Java 泛型","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"中国大学MOOC","chapterId":361,"chapterName":"课程推荐","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2953,"link":"http://www.icourse163.org/learn/ZJU-93001?tid=1002654021#/learn/content","niceDate":"2018-05-28","origin":"","projectLink":"","publishTime":1527476390000,"superChapterId":249,"superChapterName":"干货资源","tags":[],"title":"浙江大学-数据结构课程","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"MrTangFB ","chapterId":140,"chapterName":"dagger2","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2951,"link":"https://www.jianshu.com/p/b989e2cb88f6","niceDate":"2018-05-26","origin":"","projectLink":"","publishTime":1527306361000,"superChapterId":179,"superChapterName":"5.+高新技术","tags":[],"title":"Android Dagger2 从零单排(四) Dependencies与SubComponent","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"goldze","chapterId":358,"chapterName":"项目基础功能","collect":false,"courseId":13,"desc":"基于DataBinding框架，MVVM设计模式的一套快速开发库，整合Okhttp+RxJava+Retrofit+Glide等主流库，满足日常开发需求。使用该框架可以快速开发一个Android应用。\r\n","envelopePic":"http://www.wanandroid.comresources/image/pc/default_project_img.jpg","fresh":false,"id":2950,"link":"http://www.wanandroid.com/blog/show/2141","niceDate":"2018-05-26","origin":"","projectLink":"https://github.com/goldze/MVVMHabit","publishTime":1527299243000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=358"}],"title":"MVVM设计模式的一套快速开发库 MVVMHabit","type":0,"userId":-1,"visible":1,"zan":0}]
     */

    private int curPage;
    private List<DatasBean> datas;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * apkLink :
         * author : wangshub
         * chapterId : 363
         * chapterName : 创意汇
         * collect : false
         * courseId : 13
         * desc : Python 抖音机器人，论如何在抖音上找到漂亮小姐姐？

         * envelopePic : http://www.wanandroid.com/blogimgs/2316d9df-158d-4afb-9e15-39ca36e428ff.png
         * fresh : false
         * id : 2957
         * link : http://www.wanandroid.com/blog/show/2142
         * niceDate : 2018-05-29
         * origin :
         * projectLink : https://github.com/wangshub/Douyin-Bot
         * publishTime : 1527570400000
         * superChapterId : 294
         * superChapterName : 开源项目主Tab
         * tags : [{"name":"项目","url":"/project/list/1?cid=363"}]
         * title : Python 抖音机器人 Douyin-Bot
         * type : 0
         * userId : -1
         * visible : 1
         * zan : 0
         */

        private String apkLink;
        private String author;
        private int chapterId;
        private String chapterName;
        private boolean collect;
        private int courseId;
        private String desc;
        private String envelopePic;
        private boolean fresh;
        private int id;
        private String link;
        private String niceDate;
        private String origin;
        private String projectLink;
        private long publishTime;
        private int superChapterId;
        private String superChapterName;
        private String title;
        private int type;
        private int userId;
        private int visible;
        private int zan;
        private List<TagsBean> tags;

        public String getApkLink() {
            return apkLink;
        }

        public void setApkLink(String apkLink) {
            this.apkLink = apkLink;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public boolean isFresh() {
            return fresh;
        }

        public void setFresh(boolean fresh) {
            this.fresh = fresh;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getProjectLink() {
            return projectLink;
        }

        public void setProjectLink(String projectLink) {
            this.projectLink = projectLink;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public int getSuperChapterId() {
            return superChapterId;
        }

        public void setSuperChapterId(int superChapterId) {
            this.superChapterId = superChapterId;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public static class TagsBean {
            /**
             * name : 项目
             * url : /project/list/1?cid=363
             */

            private String name;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
