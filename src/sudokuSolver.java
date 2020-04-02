import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import javafx.util.Pair;

public class sudokuSolver {
    HashMap<Pair<Integer,Integer>, List<Integer>> map = new HashMap<>();
    Pair<Integer, Integer> pair;
    List<Integer> list;
    Scanner scanner = new Scanner(System.in);
    int i,j;
    int ROW = 9;
    int COLUMN = 9;
    int[][] sudoku = new int[9][9];

    public void setSudoku(int[][] sudoku){
        this.sudoku = sudoku;
    }

    //Get Sudoku input from user.
    public int[][] getSudokuInput(){
        int[][] sudoku = new int[9][9];
        for(i = 0; i < ROW; i++){
            for(j = 0; j < COLUMN; j++){
                sudoku[i][j] = scanner.nextInt();
            }
        }
        return sudoku;
    }

    //Print Sudoku.
    public void printSudoku(){
        for(i = 0; i < ROW; i++){
            for(j = 0; j < COLUMN; j++){
                System.out.print(sudoku[i][j] + " ");
            }
            System.out.println("\n");
        }
    }

    //Checking Row for element insertion.
    public boolean rowCheck(int row,int column, int element){
        for(int i = 0; i < ROW; i++ ){
            if( i != row) {
                if (element == sudoku[i][column]) {
                    return false;
                }
            }
        }
        return true;
    }

    //Checking Column for element insertion.
    public boolean columnCheck(int row, int column, int element){
        for(int j = 0; j < COLUMN; j++ ){
            if( j != column) {
                if (element == sudoku[row][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    //Checking Box for Element possiblity.
    public boolean searchBox(int row, int column, int element, int min_row, int min_col, int max_row, int max_column){
        for( int i = min_row; i < max_row; i++){
            for (int j = min_col; j < max_column; j++){
                if( i != row && j != column ){
                    if(element == sudoku[i][j]){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //Box dimension picking.
    public boolean boxCheck(int row, int column,int element){
        boolean valid;
        if(row < 3){
            if(column < 3){
                valid = searchBox(row,column,element,0,0,3,3);
            }
            else if(column < 6){
                valid = searchBox(row,column,element,0,3,3,6);
            }
            else{
                valid = searchBox(row,column,element,0,6,3,9);
            }
        }
        else if(row < 6){
            if(column < 3){
                valid = searchBox(row,column,element,3,0,6,3);
            }
            else if(column < 6){
                valid = searchBox(row,column,element,3,3,6,6);
            }
            else{
                valid = searchBox(row,column,element,3,6,6,9);
            }
        }
        else{
            if(column < 3){
                valid = searchBox(row,column,element,6,0,9,3);
            }
            else if(column < 6){
                valid = searchBox(row,column,element,6,3,9,6);
            }
            else{
                valid = searchBox(row,column,element,6,6,9,9);
            }
        }
        return valid;
    }

    //Check if the Sudoku is Valid.
    public boolean isValidSudoku(){
        boolean valid = true;
        for(i = 0; i < ROW; i++){
            for(j = 0; j < COLUMN; j++){
                int element = sudoku[i][j];
                if(element != 0) {
                    if (rowCheck(i, j, element) && columnCheck(i, j, element) && boxCheck(i, j, element)) {
                        continue;
                    }
                    else {
                        valid = false;
                        break;
                    }
                }
            }
            if(!valid){
                break;
            }
        }
        return valid;
    }

    //Update Column with new entry.
    public void updateColumn(int column,int element){
        for( int i = 0; i < ROW; i++){
            pair = new Pair<>(i,column);
            if(map.containsKey(pair)){
                list = map.get(pair);
                if(list.contains(new Integer(element))){
                    list.remove(new Integer(element));
                    if(list.size() == 1){
                        sudoku[i][column] = list.get(0);
                        //System.out.println("From Column : row = " + i + " Column = " + column + " Element = " + sudoku[i][column] );
                        list.remove(new Integer(list.get(0)));
                        map.remove(pair);
                    }
                }
            }
        }
    }

    //Update Row with new entry.
    public void updateRow(int row,int element){
        for( int j = 0; j < COLUMN; j++){
            pair = new Pair<>(row,j);
            if(map.containsKey(pair)){
                list = map.get(pair);
                if(list.contains(new Integer(element))){
                    list.remove(new Integer(element));
                    if(list.size() == 1){
                        sudoku[row][j] = list.get(0);
                        //System.out.println("From Row : row = " + row + " Column = " + j + " Element = " + sudoku[row][j] );
                        list.remove(new Integer(list.get(0)));
                        map.remove(pair);
                    }
                }
            }
        }
    }

    //Update Box with new entry.
    public void updateBox(int min_row, int min_column, int max_row, int max_column,int element){
        for( int i = min_row; i < max_row; i++ ){
            for( int j = min_column; j < max_column; j++ ){
                pair = new Pair<>(i,j);
                if(map.containsKey(pair)){
                    list = map.get(pair);
                    if(list.contains(new Integer(element))){
                        list.remove(new Integer(element));
                        if(list.size() == 1){
                            sudoku[i][j] = list.get(0);
                            //System.out.println("From Box : row = " + i + " Column = " + j + " Element = " + sudoku[i][j] );
                            list.remove(new Integer(list.get(0)));
                            map.remove(pair);
                        }
                    }
                }
            }
        }
    }

    //Box dimensions picking.
    public void updateBox(int row,int column,int element){
        if(row < 3){
            if(column < 3){
                updateBox(0,0,3,3,element);
            }
            else if(column < 6){
                updateBox(0,3,3,6,element);
            }
            else{
                updateBox(0,6,3,9,element);
            }
        }
        else if(row < 6){
            if(column < 3){
                updateBox(3,0,6,3,element);
            }
            else if(column < 6){
                updateBox(3,3,6,6,element);
            }
            else{
                updateBox(3,6,6,9,element);
            }
        }
        else{
            if(column < 3){
                updateBox(6,0,9,3,element);
            }
            else if(column < 6){
                updateBox(6,3,9,6,element);
            }
            else{
                updateBox(6,6,9,9,element);
            }
        }
    }


    //Call for Row,Column and Box check.
    public void updatePossibleElements(int max_row, int max_column, int element){
        updateRow(max_row,element);
        updateColumn(max_column,element);
        updateBox(max_row,max_column,element);
    }

    //Call for Row,Column,Box function overload.
    public void updatePossibleElements(){
        for(i = 0; i < ROW; i++){
            for(j = 0; j < COLUMN; j++){
                if(sudoku[i][j] != 0) {
                    updateRow(i,sudoku[i][j]);
                }
            }
        }
        for(i = 0; i < ROW; i++){
            for(j = 0; j < COLUMN; j++){
                if(sudoku[i][j] != 0) {
                    updateColumn(j,sudoku[i][j]);
                }
            }
        }
        for(i = 0; i < ROW; i++) {
            for (j = 0; j < COLUMN; j++) {
                if (sudoku[i][j] != 0) {
                    updateBox(i, j, sudoku[i][j]);
                }
            }
        }
    }

    //Creating basic map<pair<row,column>,list> from Sudoku.
    public void createMapElement(int row, int column){
        pair = new Pair<>(row,column);
        list = new ArrayList<>();
        for( int i = 1; i <= 9; i++){
            if(rowCheck(row,column,i) && columnCheck(row,column,i) && boxCheck(row,column,i)){
                list.add(i);
            }
        }
        if(list.size() == 1){
            sudoku[row][column] = list.get(0);
            //System.out.println("i = " + i + " j = " + j + " Elements = " + sudoku[row][column]);
            updatePossibleElements(row,column,sudoku[row][column]);
        }
        else {
            map.put(pair,list);
        }
    }

    //Element vise Call for Map creation using empty elements.
    public void getPossibleElements(){
        for(i = 0; i < ROW; i++){
            for(j = 0; j < COLUMN; j++){
                if(sudoku[i][j] == 0){
                    createMapElement(i,j);
                }
            }
        }
    }

    //Printing Possible Elements.
    void printPossibleElements(){
        for(i = 0; i < ROW; i++){
            for(j = 0; j < COLUMN; j++){
                pair = new Pair<>(i,j);
                if(map.containsKey(pair)) {
                    list = map.get(pair);
                    System.out.println("i = " + i + " j = " + j + " Elements = " + list.toString());
                }
            }
        }
    }

    //Remove elements from row.
    public void removeRowListElements(int row, int column, List<Integer> list){
        for(int j = 0; j < ROW; j++){
            pair = new Pair<>(row,j);
            if(map.containsKey(pair)) {
                List<Integer> identicalList = map.get(pair);
                if(!list.equals(identicalList)){
                    //System.out.println("Row = " + identicalList.toString() + " " + list.toString());
                    identicalList.removeAll(list);
                    map.remove(pair);
                    map.put(pair,identicalList);
                }
                if(identicalList.size() == 1){
                    sudoku[row][j] = identicalList.get(0);
                    //System.out.println("From Row : Row = " + row + " Column = " + column + " Value = " + sudoku[row][column] + " j = " + j );
                    map.remove(pair);
                }
            }
        }
    }

    public void removeIdenticalElementsFromRow(int row, int column, List<Integer> list){
        int equalListCount = 0;
        for(int j = 0; j < COLUMN; j++){
            pair = new Pair<>(row, j);
            if(map.containsKey(pair)) {
                List<Integer> identicalList = map.get(pair);
                if (list.equals(identicalList)) {
                    equalListCount++;
                }
            }
        }
        if(equalListCount == list.size()){
            removeRowListElements(row,column,list);
        }
    }

    //Remove elements from Column
    public void removeColumnListElements(int row, int column, List<Integer> list){
        for(int i = 0; i < ROW; i++){
            pair = new Pair<>(i,column);
            if(map.containsKey(pair)) {
                List<Integer> identicalList = map.get(pair);
                if(!list.equals(identicalList)){
                    //System.out.println("Column = " + identicalList.toString() + " " + list.toString());
                    identicalList.removeAll(list);
                    map.remove(pair);
                    map.put(pair,identicalList);
                }
                if(identicalList.size() == 1){
                    sudoku[i][column] = identicalList.get(0);
                    //System.out.println("From Column : Row = " + row + " Column = " + column + " Value = " + sudoku[row][column]);
                    map.remove(pair);
                }
            }
        }
    }

    public void removeIdenticalElementsFromColumn(int row, int column, List<Integer> list){
        int equalListCount = 0;
        for(int i = 0; i < COLUMN; i++){
            pair = new Pair<>(i, column);
            if(map.containsKey(pair)) {
                List<Integer> identicalList = map.get(pair);
                if (list.equals(identicalList)) {
                    equalListCount++;
                }
            }
        }
        if(equalListCount == list.size()){
            removeColumnListElements(row,column,list);
        }
    }

    //Remove elements from Box.
    public void removeBoxElements(int min_row, int min_column, int max_row,int max_column, List<Integer> list){
        for(int i = min_row; i < max_row; i++){
            for(int j = min_column; j < max_column; j++){
                pair = new Pair<>(i,j);
                if(map.containsKey(pair)){
                    List<Integer> identicalList = map.get(pair);
                    if(!identicalList.equals(list)){
                        //System.out.println("Box Elements = " + identicalList.toString() + " " + list.toString());
                        identicalList.removeAll(list);
                        map.remove(pair);
                        map.put(pair,identicalList);
                    }
                    if(identicalList.size() == 1){
                        sudoku[i][j] = identicalList.get(0);
                        //System.out.println("From Box : Row = " + i + " Column = " + j + " Value = " + sudoku[i][j]);
                        map.remove(pair);
                    }
                }
            }
        }
    }

    public void removeIdenticalElementsFromBox(int min_row, int min_column, int max_row,int max_column, List<Integer> list){
        int equalListCount = 0;
        for(int i = min_row; i < max_row; i++){
            for(int j = min_column; j < max_column; j++){
                pair = new Pair<>(i,j);
                if(map.containsKey(pair)){
                    List<Integer> identicalList = map.get(pair);
                    if(identicalList.equals(list)){
                        equalListCount++;
                    }
                }
            }
        }
        if(equalListCount == list.size()){
            //System.out.println(list.toString());
            removeBoxElements(min_row,min_column,max_row,max_column,list);
        }
    }


    public void removeIdenticalElementsFromBox(int row, int column, List<Integer> list){
        if(row < 3){
            if(column < 3){
                removeIdenticalElementsFromBox(0,0,3,3,list);
            }
            else if(column < 6){
                removeIdenticalElementsFromBox(0,3,3,6,list);
            }
            else{
                removeIdenticalElementsFromBox(0,6,3,9,list);
            }
        }
        else if(row < 6){
            if(column < 3){
                removeIdenticalElementsFromBox(3,0,6,3,list);
            }
            else if(column < 6){
                removeIdenticalElementsFromBox(3,3,6,6,list);
            }
            else{
                removeIdenticalElementsFromBox(3,6,6,9,list);
            }
        }
        else{
            if(column < 3){
                removeIdenticalElementsFromBox(6,0,9,3,list);
            }
            else if(column < 6){
                removeIdenticalElementsFromBox(6,3,9,6,list);
            }
            else{
                removeIdenticalElementsFromBox(6,6,9,9,list);
            }
        }
    }

    //Call for row,column,box removing.
    public void removeIdenticalElements(){
        for(i = 0; i < ROW; i++){
            for(j = 0; j < COLUMN; j++){
                pair = new Pair<>(i,j);
                if(sudoku[i][j] == 0 && map.containsKey(pair)){
                    list = map.get(pair);
                    removeIdenticalElementsFromRow(i,j,list);
                }
            }
        }
        for(i = 0; i < ROW; i++){
            for(j = 0; j < COLUMN; j++){
                pair = new Pair<>(i,j);
                if(sudoku[i][j] == 0 && map.containsKey(pair)){
                    list = map.get(pair);
                    removeIdenticalElementsFromColumn(i,j,list);
                }
            }
        }
        for(i = 0; i < ROW; i++){
            for(j = 0; j < COLUMN; j++){
                pair = new Pair<>(i,j);
                if(sudoku[i][j] == 0 && map.containsKey(pair)){
                    list = map.get(pair);
                    removeIdenticalElementsFromBox(i,j,list);
                }
            }
        }
    }

    //Check if element is feasibile.
    public boolean isFeasible(int row, int column, int element){
        return rowCheck(row, column, element) && columnCheck(row, column, element) && boxCheck(row, column, element);
    }

    //Recursive call for solving sudoku.
    public boolean solveSudoku(){
        int row = -1;
        int column = -1;
        boolean isEmpty = true;
        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COLUMN; j++){
                if(sudoku[i][j] == 0) {
                    row = i;
                    column = j;
                    isEmpty = false;
                    break;
                }
            }
            if(!isEmpty){
                break;
            }
        }
        if(isEmpty){
            return true;
        }
        for(int element = 1; element <= 9; element++){
            if(isFeasible(row, column, element)){
                sudoku[row][column] = element;
                if(solveSudoku()){
                    return true;
                }
                else{
                    sudoku[row][column] = 0;
                }
            }
        }
        return false;
    }

    public static void main(String[] args){

        //Class Instance for Sudoku Solver.
        sudokuSolver instance = new sudokuSolver();

        //9x9 Sudoku Input.
        int[][] sudoku = instance.getSudokuInput();

        //Global Variable Set for Sudoku.
        instance.setSudoku(sudoku);

        //Input Validity Check.
        boolean isValidSudoku = instance.isValidSudoku();

        if(isValidSudoku){

            //Get all possible elements for empty cells.
            instance.getPossibleElements();

            //Remove identical elements from list.
            instance.removeIdenticalElements();

            //Random Update 5 times for unique elements.
            for(int i = 1; i <= 5; i++){
                instance.updatePossibleElements();
            }

            //Print Possible Elements for the unfilled elements.
            instance.printPossibleElements();

            //Backtracking To Solve Sudoku.
            if(instance.solveSudoku()){

                //Print solved Sudoku.
                instance.printSudoku();

                //Valid Sudoku Check.
                if(!instance.isValidSudoku()){
                    System.out.println("WRONG SOLUTION");
                }
                else{
                    System.out.println("VALID SOLUTION");
                }
            }
            else{
                System.out.println("NO SOLUTIONS");
            }
        }
        else{
            System.out.println("INVALID INPUT");
        }
    }
}