package ir.fardan7eghlim.luckylord.models;

/**
 * Created by Meysam on 5/21/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import ir.fardan7eghlim.luckylord.R;
import ir.fardan7eghlim.luckylord.interfaces.ContactUsInterface;

/**
 * Created by Meysam on 12/20/2016.
 */

public class MixedTableModel {

    public static Boolean isAllQuestionsAnswered(ArrayList<QuestionModel> questions)
    {
       boolean result = true;
        for(int i = 0; i < questions.size(); i++)
        {
            if(questions.get(i).getAnswered() == false)
            {
                result = false;
                break;
            }
        }
        return result;
    }
    public static Integer getAggrigatedHintAmount(ArrayList<QuestionModel> questions)
    {
        Integer result = 0;
        for(int i = 0; i < questions.size(); i++)
        {
            result += questions.get(i).getPenalty();
        }
        return result;
    }

    public static Boolean isCellInAnswer(ArrayList<QuestionModel> questions, Integer cellIndex)
    {
        boolean result = false;
        for(int i = 0; i < questions.size(); i++)
        {
            if(questions.get(i).getAnswerCells().contains(cellIndex.toString()))
            {
                result  = true;
                break;
            }
        }
        return result;
    }

    //answeredCellIndex is like 55 or 15 or ... and is respective cell number in view
    public static void addCellIndexToRespectiveQuestionAnsweredCellsAndLetters(ArrayList<QuestionModel> questions, Integer answeredCellIndex, Boolean multiple)
    {

        if(!isCellInAnsweredCells(questions, answeredCellIndex))
        {
            for(int i = 0; i < questions.size(); i++)
            {
                if(questions.get(i).getAnswerCells().contains(answeredCellIndex.toString()))
                {
                    questions.get(i).getAnsweredCells().add(answeredCellIndex.toString());

                    int index = questions.get(i).getAnswerCells().indexOf(answeredCellIndex.toString());
                    char[] letters = questions.get(i).getAnswer().replaceAll("\\s+","").toCharArray();
                    String letter =String.valueOf(letters[index]);
                    questions.get(i).getAnsweredLetters().add(letter);

                    if(questions.get(i).getAnsweredCells().size() == letters.length)
                        questions.get(i).setAnswered(true);

                    if(!multiple)
                        break;
                }
            }
        }


    }

    ///cellIndex is like 55 or 15 or...and is respective cell number in view
    public static Boolean isCellInAnsweredCells(ArrayList<QuestionModel> questions, Integer cellIndex)
    {
        boolean result = false;
        for(int i = 0; i < questions.size(); i++)
        {
            if(questions.get(i).getAnsweredCells().contains(cellIndex.toString()))
            {
                result  = true;
                break;
            }
        }
        return result;
    }

    public static Boolean isCellLastCellInAnswer(ArrayList<QuestionModel> questions, Integer cellIndex)
    {
        boolean result = false;
        for(int i = 0; i < questions.size(); i++)
        {
            if(questions.get(i).getAnswerCells().contains(cellIndex.toString()))
            {
                if(new Integer(questions.get(i).getAnswerCells().get(questions.get(i).getAnswerCells().size()-1)) == cellIndex)
                    result  = true;
                break;
            }
        }
        return result;
    }

    public static QuestionModel getRelatedCellQuestion(ArrayList<QuestionModel> questions, Integer cellIndex)
    {
        QuestionModel result = null;
        for(int i = 0; i < questions.size(); i++)
        {
            if(questions.get(i).getAnswerCells().contains(cellIndex.toString()))
            {
                result = questions.get(i);
                break;
            }
        }
        return result;
    }

    public static QuestionModel getRelatedAnswerQuestion(ArrayList<QuestionModel> questions, String stringAnswer)
    {
        QuestionModel result = null;
        for(int i = 0; i < questions.size(); i++)
        {
            if(questions.get(i).getAnswer().replaceAll("\\s+","").equals(stringAnswer))
            {
                result = questions.get(i);
                break;
            }
        }
        return result;
    }

    /////////////////////////////////////////////////////////////////////////////
    public static Boolean isCellLastAnswered(ArrayList<QuestionModel> questions, Integer cellIndex)
    {
        boolean result = false;
        for(int i = 0; i < questions.size(); i++)
        {
            if(questions.get(i).getAnswerCells().contains(cellIndex.toString()))
            {
                if(questions.get(i).getAnswerCells().size() == questions.get(i).getAnsweredCells().size())
                    result  = true;
                break;
            }
        }
        return result;
    }
    ///////////////////////////////////////////////////////////////////////////////////
    public static ArrayList<QuestionModel>  swapAnswerCells(ArrayList<QuestionModel> questions, Integer firstIndex, Integer secondIndex)
    {

        int firstQuestionIndex = -1;
        int secondQuestionIndex = -1;

        boolean firstOk = false;
        boolean secondOk = false;

        for(int i = 0; i < questions.size(); i++)
        {
            if(questions.get(i).getAnswerCells().contains(firstIndex.toString()))
            {
                firstOk = true;
                firstQuestionIndex = i;
                if(firstOk && secondOk)
                    break;

            }
            if(questions.get(i).getAnswerCells().contains(secondIndex.toString()))
            {
                secondOk = true;
                secondQuestionIndex = i;
                if(firstOk && secondOk)
                    break;

            }
        }

        int firstIndexPlace = questions.get(firstQuestionIndex).getAnswerCells().indexOf(firstIndex.toString());
        int secondIndexPlace = questions.get(secondQuestionIndex).getAnswerCells().indexOf(secondIndex.toString());

        String temp = questions.get(firstQuestionIndex).getAnswerCells().get(firstIndexPlace);
        questions.get(firstQuestionIndex).getAnswerCells().set(firstIndexPlace,questions.get(secondQuestionIndex).getAnswerCells().get(secondIndexPlace));
        questions.get(secondQuestionIndex).getAnswerCells().set(secondIndexPlace,temp);


        return questions;
    }

    public static Integer countAnsweredQuestions(ArrayList<QuestionModel> questions )
    {
        Integer result = 0;
        for(int i = 0; i < questions.size(); i++)
        {
            if(questions.get(i).getAnswerCells().size() == questions.get(i).getAnsweredCells().size())
                result += 1;
        }

        return result;
    }

    public static Integer calculateTrueCellCount(ArrayList<QuestionModel> questions)  {
        Integer result = 0;
        for(int i = 0; i < questions.size(); i++)
        {
            if(questions.get(i).getAnsweredCells().size() == questions.get(i).getAnswerCells().size())
                result += 1;
        }

        return result;
    }

    public static QuestionModel  getQuestionById(ArrayList<QuestionModel> questions, BigInteger questionId)
    {
        for (int i = 0; i < questions.size(); i++)
        {
            if(questions.get(i).getId().equals(questionId))
            {
                return questions.get(i);
            }
        }
        return null;
    }
    public static QuestionModel  getQuestionByTitle(ArrayList<QuestionModel> questions, String title)
    {
        if(title.substring(0,"\u3000".length()).equals("\u3000"))
        {
            title = title.substring("\u3000".length(),title.length());
            title = title.substring(0,title.length()-"\u3000".length());
        }


        for (int i = 0; i < questions.size(); i++)
        {
            if(questions.get(i).getDescription().equals(title))
            {
                return questions.get(i);
            }
        }
        return null;
    }


}