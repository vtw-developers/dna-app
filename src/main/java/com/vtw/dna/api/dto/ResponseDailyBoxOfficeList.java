package com.vtw.dna.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(name = "ResponseDailyBoxOfficeList")
@Data
public class ResponseDailyBoxOfficeList {

    private DailyBoxOfficeResult boxOfficeResult;

    @Schema(name = "DailyBoxOfficeResult")
    @Data
    public class DailyBoxOfficeResult {
        @Schema(description = "박스오피스 종류", example = "일별 박스오피스")
        private String boxofficeType;

        @Schema(description = "박스오피스 조회 일자", example = "20120101~20120101")
        private String showRange;
        private List<DailyBoxOffice> dailyBoxOfficeList;
    }

    @Schema(name = "DailyBoxOffice")
    @Data
    public class DailyBoxOffice {
        @Schema(description = "순번", example = "1")
        private String rnum;

        @Schema(description = "해당일자의 박스오피스 순위", example = "1")
        private String rank;

        @Schema(description = "전일대비 순위의 증감분", example = "0")
        private String rankInten;

        @Schema(description = "랭킹에 신규진입여부", example = "OLD")
        private String rankOldAndNew;

        @Schema(description = "영화의 대표코드", example = "20112207")
        private String movieCd;

        @Schema(description = "영화명(국문)", example = "미션임파서블:고스트프로토콜")
        private String movieNm;

        @Schema(description = "영화의 개봉일", example = "2011-12-15")
        private String openDt;

        @Schema(description = "해당일의 매출액", example = "2776060500")
        private String salesAmt;

        @Schema(description = "해당일자 상영작의 매출총액 대비 해당 영화의 매출비율", example = "36.3")
        private String salesShare;

        @Schema(description = "전일 대비 매출액 증감분", example = "-415699000")
        private String salesInten;

        @Schema(description = "전일 대비 매출액 증감 비율", example = "-13")
        private String salesChange;

        @Schema(description = "누적매출액", example = "40541108500")
        private String salesAcc;

        @Schema(description = "해당일의 관객수", example = "353274")
        private String audiCnt;

        @Schema(description = "전일 대비 관객수 증감분", example = "-60106")
        private String audiInten;

        @Schema(description = "전일 대비 관객수 증감 비율", example = "-14.5")
        private String audiChange;

        @Schema(description = "누적관객수", example = "5328435")
        private String audiAcc;

        @Schema(description = "해당일자에 상영한 스크린수", example = "697")
        private String scrnCnt;

        @Schema(description = "해당일자에 상영된 횟수", example = "3223")
        private String showCnt;
    }

}



