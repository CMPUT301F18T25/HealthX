package com.cmput301f18t25.healthx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProblemList {
    /**
     * Creates a singleton class instance of ProblemList
     */
    private static ProblemList instance;
//    private static final ProblemList ourInstance = new ProblemList();

    private static ArrayList<Problem> problemArray;
    /**
      Static method that creates instance of Singleton ProblemListclass
     */
    public static ProblemList getInstance() {
        if (instance == null) {
            instance = new ProblemList();
        }

        return instance;
    }
    /**
     * Returns problem at a given index
     * @param index index of problem you want to return
     * */
    private ProblemList() {
        problemArray = new ArrayList<Problem>();

    }
    /**
     * Adds a problem to ProblemList
     * @param problem problem to be added
     * */
    public  void addToProblemList(Problem problem) {
        //Problem  newProblem = new Problem(problem.getTitle(), problem.getDescription(), problem.getDate());
        problemArray.add(problem);
        sortArray();
    }
    /**
     * Edits a problem in ProblemList
     * @param e the new edited problem to insert into list
     * @param index the index of problem you wish to edit
     * */
    // Edits a specific problem -- Note the function takes the index of the old problem and a new problem object
    public void EditProblem(int index, Problem e) {
        problemArray.set(index, e);
        sortArray();
    }

    /**
     * Returns number of problems in ProblemList
     * */
    public int getListCount() {
        return problemArray.size();
    }

    /**
     * Returns problem at a given index
     * @param index index of problem you want to return
     * */
    public Problem getElementByIndex(int index) {
        return problemArray.get(index);

    }
    /**
     * Removes problem at a given index
     * @param index index of problem you want to remove
     * */
    public void removeProblemFromList(int index) {
        problemArray.remove(index);
    }

    /**
     * Sorts array by date from recent to least recent
     * */
    public void sortArray() {
        Collections.sort(problemArray, new Comparator<Problem>() {
            @Override
            public int compare(Problem t1, Problem t2) {
                return t2.getDate().compareTo(t1.getDate());
            }
        });
    }
}
