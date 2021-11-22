package sa.edu.getsocial.Models;

import java.util.List;

public class QuizModel {
    String  id;
    String course_name;
    String date;
    String grade;

    List<QuestionModel> question_list;



    public QuizModel() {
    }

    public QuizModel(String id, String course_name, String date, String grade, List<QuestionModel> question_list) {
        this.id = id;
        this.course_name = course_name;
        this.date = date;
        this.grade = grade;
        this.question_list = question_list;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<QuestionModel> getQuestion_list() {
        return question_list;
    }

    public void setQuestion_list(List<QuestionModel> question_list) {
        this.question_list = question_list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}
