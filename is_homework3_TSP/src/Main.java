import java.util.*;

public class Main {

    public static void _print(List<City> best) {
        for (City city: best) {
            System.out.println(city.getX() + " " + city.getY());
        }
    }

    public static List<City> swap(List<City> toSwap, int N) {
        List<City> tmp = new ArrayList<>(toSwap);
        int index1 = new Random().nextInt(N - 1) +1;
        int index2 = new Random().nextInt(N - 1) +1;

        City temp = new City(tmp.get(index1).getX(), tmp.get(index1).getY());

        tmp.get(index1).setX(tmp.get(index2).getX());
        tmp.get(index1).setY(tmp.get(index2).getY());

        tmp.get(index2).setX(temp.getX());
        tmp.get(index2).setY(temp.getY());

        return tmp;
    }

    static int calculateWeight(City prev, City curr) {
        return Math.abs(prev.getX() - curr.getX()) + Math.abs(prev.getY() - curr.getY());
    }

    static int fitness(int N, List<City> currCity) {
        int weight = 0;

        int j =0;
        for (int i=1; i<=N -1; ++i) {
            weight += calculateWeight(currCity.get(j++), currCity.get(i));
        }

        return weight;
    }
    //crossover
    static List<City> generateNewPath( int N, List<City> newCity, List<City> currCity) { // cross One Point
        List<City> tmp = new ArrayList<>();
        int index1 = new Random().nextInt(N);
        int index2 = new Random().nextInt(N);

        if (index2 < index1) {
            int tmp1 = index1;
            index1 = index2;
            index2 = tmp1;
        }

        for(int i=index1; i<=index2; ++i) {
            tmp.add(currCity.get(i));
        }

        for(int i=0; i<N; ++i) {
            if (!tmp.contains(newCity.get(i))) {
                tmp.add(newCity.get(i));
            }
        }

        return tmp;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        List<City> cities = new ArrayList<>();
        Set<Integer> containsCityX = new HashSet<>();
        Set<Integer> containsCityY = new HashSet<>();

        int x,y;
        for (int i=0; i<N;) {
            //cities.add(new City(scanner.nextInt(), scanner.nextInt()));
            int x1 = new Random().nextInt(N);
            int y1 = new Random().nextInt(N);
            if (!containsCityX.contains(x1) && !containsCityY.contains(y1)) {
                containsCityX.add(x1);
                containsCityY.add(y1);
                cities.add(new City(x1, y1));
                ++i;
            }
        }

        List<List<City>> all = new ArrayList<>();

        // generate new Paths
        for(int i=0; i<N; ++i) {
            List<City> swappedCities = swap(cities, N);

            List<City> generated = generateNewPath( N, swappedCities, cities);

            all.add(generated);
        }

        int minPath = Integer.MAX_VALUE;
        Stack<Path> allPaths = new Stack<>();

        // add the best paths and get last(first from stack) they are the best
        for(List<City> tmp : all) {
            int currWeight = fitness(N, tmp);
            if (minPath >= currWeight) {
                minPath = currWeight;
                allPaths.add(new Path(minPath, tmp));
            }
        }

        all.clear();
        List<City> best = new ArrayList<>();
        int bestWeight = Integer.MAX_VALUE;
        // loop N times to get the best solution
        for(int i=0; i<N && allPaths.size() >= 2; ++i) {
            List<City> best1 = allPaths.pop().path;
            List<City> best2 = allPaths.pop().path;
            allPaths.clear();
            // loop again inside to generate crossovers
            for(int j=0; j<N; ++j) {
                List<City> generated = generateNewPath( N, best2, best1);
                int currWeight = fitness(N, generated);
                if (minPath >= currWeight) {
                    minPath = currWeight;
                    allPaths.add(new Path(minPath, generated));
                    if (bestWeight > minPath) {
                        bestWeight = minPath;
                        best = generated;
                    }
                }
                all.add(generated);
            }
            if (i == 0 || i == 2 || i == 3 || i == 1) {
                _print(best);
                System.out.println();
            }
        }

        _print(best);

    }
}
