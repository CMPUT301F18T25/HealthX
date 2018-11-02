package com.cmput301f18t25.healthx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProblemList {
    private static ProblemList instance;
//    private static final ProblemList ourInstance = new ProblemList();

    private static ArrayList<Problem> problemArray;
    public static ProblemList getInstance() {
        if (instance == null) {
            instance = new ProblemList();
        }

        return instance;
    }

    private ProblemList() {
        problemArray = new ArrayList<Problem>();

    }

    public  void addToProblemList(Problem problem) {
        //Problem  newProblem = new Problem(problem.getTitle(), problem.getDescription(), problem.getDate());
        problemArray.add(problem);
        sortArray();
    }

    // Edits a specific problem -- Note the function takes the index of the old problem and a new problem object
    public void EditProblem(int index, Problem e) {
        problemArray.set(index, e);
        sortArray();
    }

    // self -explanatory
    public int getListCount() {
        return problemArray.size();
    }

    public Problem getElementByIndex(int index) {
        return problemArray.get(index);
    }

    public void removeProblemFromList(int index) {
        problemArray.remove(index);
    }

    // Sorts the array by date
    public void sortArray() {
        Collections.sort(problemArray, new Comparator<Problem>() {
            @Override
            public int compare(Problem t1, Problem t2) {
                return t2.getDate().compareTo(t1.getDate());
            }
        });
    }
}
