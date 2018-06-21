package com.xy.wanandroid.data.main;

import java.util.List;

/**
 * Created by jxy on 2018/6/21.
 */

public class SearchResult {

    /**
     * curPage : 1
     * datas : [{"apkLink":"","author":"Sam哥哥","chapterId":245,"chapterName":"集合相关","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2973,"link":"https://blog.csdn.net/linsongbin1/article/details/54581787","niceDate":"2018-06-01","origin":"","projectLink":"","publishTime":1527842877000,"superChapterId":245,"superChapterName":"Java深入","tags":[],"title":"线程<em class='highlight'>安全<\/em>的CopyOnWriteArrayList介绍","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"Shawn_Dut","chapterId":98,"chapterName":"WebView","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2837,"link":"https://juejin.im/post/58a037df86b599006b3fade4","niceDate":"2018-04-22","origin":"","projectLink":"","publishTime":1524378206000,"superChapterId":98,"superChapterName":"网络访问","tags":[],"title":"android WebView详解，常见漏洞详解和<em class='highlight'>安全<\/em>源码","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"ivonhoe","chapterId":74,"chapterName":"反编译","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2689,"link":"http://www.wanandroid.com/blog/show/2091","niceDate":"2018-03-21","origin":"","projectLink":"","publishTime":1521596009000,"superChapterId":185,"superChapterName":"热门专题","tags":[],"title":"Android<em class='highlight'>安全<\/em>之APP去广告","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"罗升阳","chapterId":0,"chapterName":"","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2583,"link":"http://blog.csdn.net/luoshengyang/article/details/17131835","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380205000,"superChapterId":0,"superChapterName":"","tags":[],"title":"从NDK在非Root手机上的调试原理探讨Android的<em class='highlight'>安全<\/em>机制","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"罗升阳","chapterId":0,"chapterName":"","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2572,"link":"http://blog.csdn.net/luoshengyang/article/details/38054645","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380203000,"superChapterId":0,"superChapterName":"","tags":[],"title":"SEAndroid<em class='highlight'>安全<\/em>机制中的进程<em class='highlight'>安全<\/em>上下文关联分析","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"罗升阳","chapterId":0,"chapterName":"","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2573,"link":"http://blog.csdn.net/luoshengyang/article/details/37749383","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380203000,"superChapterId":0,"superChapterName":"","tags":[],"title":"SEAndroid<em class='highlight'>安全<\/em>机制中的文件<em class='highlight'>安全<\/em>上下文关联分析","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"罗升阳","chapterId":0,"chapterName":"","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2574,"link":"http://blog.csdn.net/luoshengyang/article/details/37613135","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380203000,"superChapterId":0,"superChapterName":"","tags":[],"title":"SEAndroid<em class='highlight'>安全<\/em>机制框架分析","type":0,"userId":-1,"visible":1,"zan":0}]
     */

    private int curPage;
    private List<DatasBean> datas;

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
         * author : Sam哥哥
         * chapterId : 245
         * chapterName : 集合相关
         * collect : false
         * courseId : 13
         * desc :
         * envelopePic :
         * fresh : false
         * id : 2973
         * link : https://blog.csdn.net/linsongbin1/article/details/54581787
         * niceDate : 2018-06-01
         * origin :
         * projectLink :
         * publishTime : 1527842877000
         * superChapterId : 245
         * superChapterName : Java深入
         * tags : []
         * title : 线程<em class='highlight'>安全</em>的CopyOnWriteArrayList介绍
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
        private List<?> tags;

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

        public List<?> getTags() {
            return tags;
        }

        public void setTags(List<?> tags) {
            this.tags = tags;
        }
    }
}
