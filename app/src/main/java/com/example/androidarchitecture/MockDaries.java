package com.example.androidarchitecture;

import java.util.LinkedHashMap;

public class MockDaries {

    private static final String CONTENT = "统筹推进新冠肺炎疫情防控和经济社会发展工作部署会议23日在京召开。中共中央总书记、国家主席、中央军委主席习近平出席会议并发表重要讲话。李克强主持会议，栗战书、汪洋、王沪宁、赵乐际、韩正出席会议。会议以电视电话会议形式召开。 ";

    public static LinkedHashMap<String, DiaryModel> mock() {
        LinkedHashMap<String, DiaryModel> data = new LinkedHashMap<>();

        for (int i = 0; i < 20; i++) {
            DiaryModel diaryModel = new DiaryModel("2020-02-23 学习架构知识", CONTENT);
            data.put(diaryModel.getId(), diaryModel);
        }

        return data;
    }
}
