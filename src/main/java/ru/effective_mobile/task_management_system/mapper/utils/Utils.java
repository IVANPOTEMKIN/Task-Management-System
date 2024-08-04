package ru.effective_mobile.task_management_system.mapper.utils;

public class Utils {

    public static final String EXPRESSION_FOR_CONCAT_FULL_NAME_OF_AUTHOR_COMMENT =
            "java(comment.getAuthor().getFirstName() + \" \" + comment.getAuthor().getLastName())";

    public static final String EXPRESSION_FOR_CONCAT_FULL_NAME_OF_AUTHOR_TASK =
            "java(task.getAuthor().getFirstName() + \" \" + task.getAuthor().getLastName())";

    public static final String EXPRESSION_FOR_CONCAT_FULL_NAME_OF_PERFORMER_TASK =
            "java(task.getPerformer().getFirstName() + \" \" + task.getPerformer().getLastName())";
}