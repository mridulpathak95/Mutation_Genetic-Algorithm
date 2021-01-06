import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class Comp6731 {
	static int popsize = 4;
	static double mutationProb = 1;
	static double crossoverprob = 75;
	static ArrayList<ArrayList<Integer>> population;
	static ArrayList<ArrayList<Integer>> keysets, keysets2;
	static HashSet<ArrayList<Integer>> populationtemp;
	static ArrayList<Integer> chromosome;
	static HashMap<ArrayList<Integer>, Float> FitnessResult;
	static float fitnessSum = 0;
	static ArrayList<Integer> templist;
	static double fitnessmaxstop = 0.0;
	static int stopcount;

	public static void main(String[] args) {
		FitnessResult = new HashMap<>();
		Comp6731 class1 = new Comp6731();
		Scanner scanner = new Scanner(System.in);
		population = new ArrayList<>();
		keysets = new ArrayList<>();
		keysets2 = new ArrayList<>();
		populationtemp = new HashSet<>();
		chromosome = new ArrayList<>();
		class1.CreatePopulation(popsize);
		class1.FitnessCalculations();
		class1.sorting(FitnessResult);
		System.out.println("Project COMP 6731 by Pazim Goyal");
		System.out
				.println("Mutation Probability: " + mutationProb + "   and   CrossOver Probability: " + crossoverprob);
		System.out.println("Initial Population Size: " + population.size());
		System.out.println("Initial Population: " + population);
		int i = 1;
		while (true) {
			double tempfitness = 0;
			System.out.println("----------------------------------------------------------------------------");
			System.out.println("Generation: " + i);
			System.out.println("Individual and Fitness: " + FitnessResult);
			keysets.clear();
			keysets.addAll(FitnessResult.keySet());
			for (int j = 0; j <= keysets.size() / 2; j++) {
				ArrayList<Integer> ans1 = keysets.get(j);
				ArrayList<Integer> ans2 = keysets.get(j + 1);
				class1.crossover(ans1, ans2);
				class1.afterMutation();
				j++;
			}
			i++;
			double tempval = Collections.max(FitnessResult.values());
			tempfitness = tempval;
			if (tempval > fitnessmaxstop) {
				fitnessmaxstop = tempval;
			} else if (tempval == fitnessmaxstop && tempfitness == tempval) {
				stopcount++;
			} else {
				stopcount = 0;
			}
			if (tempval >= 0.6 && stopcount >= 5)
				break;
		}
		double tempval = Collections.max(FitnessResult.values());
System.out.println("--------------------------------------------------------");
keysets2.clear();
keysets2.addAll(FitnessResult.keySet());

System.out.println("Chromosome Selected: "+keysets2.get(0) + "  with Fitness Value: "+tempval);
	}

	public void crossover(ArrayList<Integer> crossarray1, ArrayList<Integer> crossarray2) {
		Random random = new Random();
		ArrayList<Integer> crossarray3 = (ArrayList<Integer>) crossarray1.clone();
		ArrayList<Integer> crossarray4 = (ArrayList<Integer>) crossarray2.clone();
		System.out.println("Selected Parent 1: " + crossarray1 + "and Selected Parent 2: " + crossarray2);

		if (random.nextDouble() * 100 < crossoverprob) {
			int tempRandomVal = random.nextInt(3) + 1;
			int temp;

			for (int i = tempRandomVal; i < 4; i++) {
				temp = (int) crossarray3.get(i);
				crossarray3.set(i, crossarray4.get(i));
				crossarray4.set(i, temp);
			}
			System.out.println("Crossover at gene:" + tempRandomVal + " Parent 1: " + crossarray3 + "and Parent 2: "
					+ crossarray4);
		} else {
			System.out.println("No Crossover");
		}
		Comp6731 class1 = new Comp6731();
		double mutationProbCheck = random.nextDouble() * 10;
		class1.mutation(crossarray3);
		class1.mutation(crossarray4);
	}

	public void afterMutation() {
		Comp6731 class1 = new Comp6731();
		FitnessCalculations();
		class1.sorting(FitnessResult);
		System.out.println("New Fitness: " + FitnessResult);
		keysets2.clear();
		keysets2.addAll(FitnessResult.keySet());
		// System.out.println(arrayList);
		for (int i = 4; i < keysets2.size(); i++) {
			FitnessResult.remove(keysets2.get(i));
		}
		population.clear();
		population.addAll(FitnessResult.keySet());
		// System.out.println(FitnessResult);
	}

	public void mutation(ArrayList<Integer> mutationarr) {
		Comp6731 class1 = new Comp6731();
		Random random = new Random();
		double mutationProbCheck = random.nextDouble() * 10;
		if (mutationProbCheck <= mutationProb) {
			int tempmutval = random.nextInt(4);
			int val = mutationarr.get(tempmutval);
			if (val == 1)
				mutationarr.set(tempmutval, 0);
			else
				mutationarr.set(tempmutval, 1);
			System.out.println("After Mutation: " + mutationarr);
		} else {
			System.out.println("No Mutation: Children remains" + mutationarr);
		}
		/*
		 * boolean answer = class1.constrain(mutationarr.get(0), mutationarr.get(1),
		 * mutationarr.get(2), mutationarr.get(3));
		 */boolean checking = population.contains(mutationarr);
		population.add(mutationarr);
		popsize += 1;
	}

	public void FitnessCalculations() {
		Comp6731 class1 = new Comp6731();
		fitnessSum = 0;
		FitnessResult.clear();
		for (int i = 0; i < population.size(); i++) {
			chromosome = population.get(i);
			float cc = class1.fitness(chromosome.get(0), chromosome.get(1), chromosome.get(2), chromosome.get(3));
			boolean answer = class1.constrain(chromosome.get(0), chromosome.get(1), chromosome.get(2),
					chromosome.get(3));
			if (answer)
				FitnessResult.put(chromosome, cc);
			else
				FitnessResult.put(chromosome, (float) 0.0);
			fitnessSum += cc;
		}
	}

	public float fitness(int a, int b, int c, int d) {
		double ans = (0.2 * a) + (0.3 * b) + (0.5 * c) + (0.1 * d);
		return (float) ans;
	}

	public boolean constrain(int a, int b, int c, int d) {
		double comp1 = 3.1;
		double comp2 = 2.5;
		double comp3 = 0.4;
		double ans1 = (0.5 * a) + (1.0 * b) + (1.5 * c) + (0.1 * d);
		double ans2 = (0.3 * a) + (0.8 * b) + (1.5 * c) + (0.4 * d);
		double ans3 = (0.2 * a) + (0.2 * b) + (0.3 * c) + (0.1 * d);
		return (ans1 <= comp1) && (ans2 <= comp2) && (ans3 <= comp3);
	}

	public void CreatePopulation(int popsize) {
		Comp6731 class1 = new Comp6731();
		Random random = new Random();
		int count = 0;
		while (true) {
			ArrayList<Integer> temp = new ArrayList<>();
			for (int j = 0; j < 4; j++) {
				temp.add(random.nextInt(2));
			}
			boolean bb = class1.constrain(temp.get(0), temp.get(1), temp.get(2), temp.get(3)); // check if the

			if (bb) {
				populationtemp.add(temp);
			}
			if (populationtemp.size() >= popsize) {
				population.addAll(populationtemp);
				break;
			}
		}
	}

	public int rouletteWheel() {
		Random random = new Random();
		Object[] fitarr = FitnessResult.values().toArray();
		int count = 0;
		int ans = 0;
		float total = 0;
		float point = fitnessSum * random.nextFloat();
		while (total <= point) {
			total += (float) fitarr[count];
			count++;
		}

		return count;
	}

	public HashMap<ArrayList<Integer>, Float> sorting(HashMap<ArrayList<Integer>, Float> temp) {
		MapSortingCustom mapUtil = new MapSortingCustom();
		FitnessResult = (HashMap<ArrayList<Integer>, Float>) MapSortingCustom.sortByValue(FitnessResult);
		return FitnessResult;
	}

}