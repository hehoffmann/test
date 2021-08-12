package h07;

import java.util.Arrays;

import h07.experiment.ChickenNuggets;

public class Main {
	public static void main(String[] args) {
		for (int i = 0; i < 100000; i++) {
			int[] arr = ChickenNuggets.computePackageNumbers(i);
			System.out.println(i + ":\t" + (arr != null ? Arrays.toString(arr) : "not possible"));
		}
	}
}
