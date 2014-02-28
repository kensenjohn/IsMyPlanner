package com.events.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2/27/14
 * Time: 11:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class Yahtzee {
    public static void main(String[] args){
        final Integer NUM_OF_DICE = 5;
        final Integer DICE_FACES = 10;
        final Integer DICE_ROLL_TRY_LIMIT = 10000;
        final Integer ATTEMPT_LIMIT = 10;
        for( int attempt = 0; attempt <ATTEMPT_LIMIT;attempt++  ) {

            Integer iTryCount = 0;
            Integer iTotalYahtzee = 0;
            while(true){

                ArrayList<Integer> arrDiceRoleNum = new ArrayList<Integer>();
                for( int i = 0;i<NUM_OF_DICE;i++){
                    Random randomGenerator = new Random();
                    int randomInt = 0;
                    while(randomInt<=0) {
                        randomInt = randomGenerator.nextInt(DICE_FACES);

                        if(randomInt>0){
                            arrDiceRoleNum.add(randomInt);
                        }
                    }
                }


                HashMap<Integer,Integer> hmDiceRoleNum = new HashMap<Integer,Integer>();
                for(Integer iNum : arrDiceRoleNum ){
                    if(iNum>0){
                        hmDiceRoleNum.put(iNum,iNum);
                    }

                }

                iTryCount++;
                if(hmDiceRoleNum!=null && hmDiceRoleNum.size() == 1 && arrDiceRoleNum.size()==NUM_OF_DICE){
                    //System.out.println("Yahtzee = " + arrDiceRoleNum + " --> iTryCount : " + iTryCount);
                    iTotalYahtzee++;
                } else {
                    //System.out.println("faile = " + hmDiceRoleNum + " --> iTryCount : " + iTryCount);
                }

                if(iTryCount > DICE_ROLL_TRY_LIMIT){
                    break;
                }
            }
            System.out.println( iTotalYahtzee);
        }

    }
}
