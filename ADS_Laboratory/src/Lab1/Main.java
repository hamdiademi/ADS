package Lab1;

import java.util.Scanner;

class QuarterlySales {

    private int numOfSales;
    private int[] revenues;
    private int quarterNo;

    public QuarterlySales(int numOfSales, int[] revenues, int quarterNo) {
        this.numOfSales = numOfSales;
        this.revenues = revenues;
        this.quarterNo = quarterNo;
    }

    public int sumQuarterlySales() {
        int totalRevenue = 0;
        for (int i = 0; i < numOfSales; i++) {
            totalRevenue += revenues[i];
        }
        return totalRevenue;
    }

    public int getQuarterNo() {
        return quarterNo;
    }
}

class SalesPerson {

    private String name;
    private QuarterlySales[] quarters;

    public SalesPerson(String name, QuarterlySales[] quarters) {
        this.name = name;
        this.quarters = quarters;
    }

    public int sumTotalSales() {
        int totalRevenue = 0;
        for (int i = 0; i < quarters.length; i++) {
            totalRevenue += quarters[i].sumQuarterlySales();
        }
        return totalRevenue;
    }

    @Override
    public String toString() {
        String result = name + "   ";
        for (int i = 0; i < quarters.length; i++) {
            result += quarters[i].sumQuarterlySales() + "   ";
        }
        result += sumTotalSales();
        return result;
    }

    public String getName() {
        return name;
    }
}

public class Main {

    public static SalesPerson salesChampion(SalesPerson[] arr) {
        SalesPerson champion = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].sumTotalSales() > champion.sumTotalSales()) {
                champion = arr[i];
            }
        }
        return champion;
    }

    public static void table(SalesPerson[] arr) {
        System.out.println("SP   1   2   3   4   Total");
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i].toString());
        }
    }

    public static void main(String[] args) {
        int n;
        Scanner input = new Scanner(System.in);
        n = input.nextInt();
        SalesPerson[] arr = new SalesPerson[n];
        for (int i = 0; i < n; i++) {
            String name = input.next();
            QuarterlySales[] quarters = new QuarterlySales[4];

            for (int j = 0; j < 4; j++) {
                int numOfSales = input.nextInt();
                int[] revenues = new int[numOfSales];
                for (int k = 0; k < numOfSales; k++) {
                    revenues[k] = input.nextInt();
                }
                quarters[j] = new QuarterlySales(numOfSales, revenues, j + 1);
            }

            arr[i] = new SalesPerson(name, quarters);
        }

        table(arr);
        System.out.println("\nSALES CHAMPION: " + salesChampion(arr).getName());
    }
}
