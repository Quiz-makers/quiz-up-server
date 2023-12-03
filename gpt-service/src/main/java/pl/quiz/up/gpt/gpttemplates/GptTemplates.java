package pl.quiz.up.gpt.gpttemplates;

public class GptTemplates {

    private static final String QUIZ_FROM_TITLE_TEMPLATE =
        "Generate quiz with following requirements:\n" +
                "1. Quiz topic: %s\n" +
                "2. Category: %s\n" +
                "3. Number of questions: %s\n" +
                "4. Answers per question: %s\n" +
                "5. Quiz language: The same one in which the quiz topic was written\n" +
                "6. Format: JSON\n" +
                "7. 100%% completed, ready to parse JSON (convertible into Java object)\n" +
                "8. JSON Structure template:\n" +
                "{\n" +
                "    \"title\": \"xxx\",\n" +
                "    \"summary\": \"xxx\",\n" +
                "    \"description\": \"xxx\",\n" +
                "    \"quizQuestionsWithAnswersEntities\": {\n" +
                "        \"q1\": {\n" +
                "            \"type\": \"1\",\n" +
                "            \"question\": \"xxx\",\n" +
                "            \"questionImage\": null,\n" +
                "            \"score\": \"xxx\",\n" +
                "            \"difficultyLevel\": \"put here number from 1 to 10 according to question difficulty level\",\n" +
                "            \"visibleInQuiz\": true,\n" +
                "            \"questionAnswersEntities\": [\n" +
                "                {\n" +
                "                    \"answer\": \"answer1\",\n" +
                "                    \"correct\": true,\n" +
                "                    \"active\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"answer\": \"answer1\",\n" +
                "                    \"correct\": false,\n" +
                "                    \"active\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"answer\": \"answer1\",\n" +
                "                    \"correct\": false,\n" +
                "                    \"active\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"answer\": \"answer1\",\n" +
                "                    \"correct\": false,\n" +
                "                    \"active\": true\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        \"q2\": {\n" +
                "            \"type\": \"1\",\n" +
                "            \"question\": \"yyyy\",\n" +
                "            \"questionImage\": null,\n" +
                "            \"score\": \"xxx\",\n" +
                "            \"difficultyLevel\": \"put here number from 1 to 10 according to question difficulty level\",\n" +
                "            \"visibleInQuiz\": true,\n" +
                "            \"questionAnswersEntities\": [\n" +
                "                {\n" +
                "                    \"answer\": \"q2_answer1\",\n" +
                "                    \"correct\": true,\n" +
                "                    \"active\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"answer\": \"q2_answer1\",\n" +
                "                    \"correct\": false,\n" +
                "                    \"active\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"answer\": \"q2_answer1\",\n" +
                "                    \"correct\": false,\n" +
                "                    \"active\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"answer\": \"q2_answer1\",\n" +
                "                    \"correct\": false,\n" +
                "                    \"active\": true\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}";

    private static final String QUIZ_FROM_TEXT_TEMPLATE =
        "Generate quiz with following requirements:\n" +
                "1. Quiz content is created from: %s\n" +
                "2. Category: %s\n" +
                "3. Number of questions: %s\n" +
                "4. Answers per question: %s\n" +
                "5. Quiz language: The same one in which the content for quiz is provided\n" +
                "6. Format: JSON\n" +
                "7. 100%% completed, ready to parse JSON (convertible into Java object)\n" +
                "8. JSON Structure template:\n" +
                "{\n" +
                "    \"title\": \"xxx\",\n" +
                "    \"summary\": \"xxx\",\n" +
                "    \"description\": \"xxx\",\n" +
                "    \"quizQuestionsWithAnswersEntities\": {\n" +
                "        \"q1\": {\n" +
                "            \"type\": \"1\",\n" +
                "            \"question\": \"xxx\",\n" +
                "            \"questionImage\": null,\n" +
                "            \"score\": \"xxx\",\n" +
                "            \"difficultyLevel\": \"put here number from 1 to 10 according to question difficulty level\",\n" +
                "            \"visibleInQuiz\": true,\n" +
                "            \"questionAnswersEntities\": [\n" +
                "                {\n" +
                "                    \"answer\": \"answer1\",\n" +
                "                    \"correct\": true,\n" +
                "                    \"active\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"answer\": \"answer1\",\n" +
                "                    \"correct\": false,\n" +
                "                    \"active\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"answer\": \"answer1\",\n" +
                "                    \"correct\": false,\n" +
                "                    \"active\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"answer\": \"answer1\",\n" +
                "                    \"correct\": false,\n" +
                "                    \"active\": true\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        \"q2\": {\n" +
                "            \"type\": \"1\",\n" +
                "            \"question\": \"yyyy\",\n" +
                "            \"questionImage\": null,\n" +
                "            \"score\": \"xxx\",\n" +
                "            \"difficultyLevel\": \"put here number from 1 to 10 according to question difficulty level\",\n" +
                "            \"visibleInQuiz\": true,\n" +
                "            \"questionAnswersEntities\": [\n" +
                "                {\n" +
                "                    \"answer\": \"q2_answer1\",\n" +
                "                    \"correct\": true,\n" +
                "                    \"active\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"answer\": \"q2_answer1\",\n" +
                "                    \"correct\": false,\n" +
                "                    \"active\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"answer\": \"q2_answer1\",\n" +
                "                    \"correct\": false,\n" +
                "                    \"active\": true\n" +
                "                },\n" +
                "                {\n" +
                "                    \"answer\": \"q2_answer1\",\n" +
                "                    \"correct\": false,\n" +
                "                    \"active\": true\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}";


    public static String getTemplateForQuizFromTitleQuery(String title, String category, int numberOfQuestions, int getAnswersPerQuestion) {
        return String.format(QUIZ_FROM_TITLE_TEMPLATE, title, category, numberOfQuestions, getAnswersPerQuestion);
    }

    public static String getTemplateForQuizFromTextQuery(String text, String category, int numberOfQuestions, int getAnswersPerQuestion) {
        return QUIZ_FROM_TEXT_TEMPLATE.formatted(text, category, String.valueOf(numberOfQuestions), String.valueOf(getAnswersPerQuestion));
    }
}
