package com.campfire.smeal.dto.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Criteria {
    /* 현재 페이지 */
    private int pageNum;

    /* 한 페이지 당 보여질 게시물 갯수 */
    private int amount;

    /* 스킵 할 게시물 수( (pageNum-1) * amount ) */
    private int skip;

    /* 검색어 키워드 */
    private String keyword;

    /* 검색 타입 */
    private String type;

    /* 검색 타입 배열 */
    private String[] typeArr;

    public Criteria() {
        this(1,10);
        this.skip = 0;
    }

    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
        // 게시글 시작번호
        this.skip = (pageNum-1)*amount;
    }

    //////////////
    public int getPageStart() { //게시글 시작번호
        return (this.pageNum - 1) * amount;
    }

    public void setPageNum(int pageNum) {
        this.skip= (pageNum-1)*this.amount;
        if (pageNum <= 0) {
            this.pageNum = 1;
        } else {
            this.pageNum = pageNum;
        }
    }

    public void setAmount(int pageCount) {
        this.skip = (this.pageNum - 1) * amount;
        int cnt = this.amount;
        if (pageCount != cnt) {
            this.amount = cnt;
        } else {
            this.amount = pageCount;
        }
    }


//
//    public int getPageNum() {
//        return pageNum;
//    }
////
//    public void setPageNum(int pageNum) {
//
//        this.skip= (pageNum-1)*this.amount;
//
//        this.pageNum = pageNum;
//    }
//
//    public int getAmount() {
//        return amount;
//    }
//
//    public void setAmount(int amount) {
//
//        this.skip= (this.pageNum-1)*amount;
//
//        this.amount = amount;
//    }
//
//
//    public int getSkip() {
//        return skip;
//    }
//
//    public void setSkip(int skip) {
//        this.skip = skip;
//    }
//
//    public String getKeyword() {
//        return keyword;
//    }
//
//    public void setKeyword(String keyword) {
//        this.keyword = keyword;
//    }
//
//    public String getType() {
//        return type;
//    }
//
    public void setType(String type) {
        this.type = type;
        this.typeArr = type.split("");
    }
//
//    public String[] getTypeArr() {
//        return typeArr;
//    }
//
//    public void setTypeArr(String[] typeArr) {
//        this.typeArr = typeArr;
//    }
//
//    @Override
//    public String toString() {
//        return "Criteria [pageNum=" + pageNum + ", amount=" + amount + ", skip=" + skip + ", keyword=" + keyword
//                + ", type=" + type + ", typeArr=" + Arrays.toString(typeArr) + "]";
//    }


}
