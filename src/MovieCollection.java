import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();


        searchTerm = searchTerm.toLowerCase();


        ArrayList<Movie> results = new ArrayList<Movie>();


        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        sortResults(results);


        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();


            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }
    private void sortResults2(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            double tempRating = temp.getUserRating();

            int possibleIndex = j;
            while(possibleIndex> 0 && tempRating>=(listToSort.get(possibleIndex-1).getUserRating()))
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }
    private void sortResults3(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            int tempRevenue = temp.getRevenue();

            int possibleIndex = j;
            while(possibleIndex> 0 && tempRevenue>=(listToSort.get(possibleIndex-1).getRevenue()))
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }
    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter a cast search term: ");
        String castFind = scanner.nextLine();
        castFind = castFind.substring(0, 1).toUpperCase() + castFind.substring(1);

        ArrayList<String> castList = new ArrayList<String>();
        for(int i = 0; i<movies.size();i++){
            String cast = movies.get(i).getCast();
            while(cast.indexOf("|")!=-1){
                String a = cast.substring(0,cast.indexOf("|"));
                if(a.contains(castFind)){
                    if(!castList.contains(a)){
                        castList.add(a);
                    }
                }
                cast = cast.substring(cast.indexOf("|")+1);
            }
            if(!castList.contains(cast)&&cast.contains(castFind)){
                castList.add(cast);
            }
        }

        Collections.sort(castList);
        for(int q = 0; q < castList.size(); q++){
            int choiceNum = q+1;
            System.out.println("" + choiceNum + ". " + castList.get(q));
        }
        System.out.println("Select a cast member to see their movies");
        System.out.print("Enter number: ");
        int choices = scanner.nextInt();
        scanner.nextLine();
        String person = castList.get(choices-1);
        ArrayList<Movie> results = new ArrayList<Movie>();
        for(int i = 0; i<movies.size();i++){
            if (movies.get(i).getCast().contains(person)){
                results.add(movies.get(i));
            }
        }
        sortResults(results);
        for (int i = 0; i < results.size(); i++){
            String title = results.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = results.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String keyword = scanner.nextLine();
        keyword = keyword.toLowerCase();
        ArrayList<Movie> results = new ArrayList<Movie>();
        for(int i = 0; i < movies.size(); i++){
            String movieKeywords = movies.get(i).getKeywords();
            ArrayList<String> movieKeywordsList = new ArrayList<String>();
            while(movieKeywords.indexOf("|")!=-1){
                movieKeywordsList.add(movieKeywords.substring(0,movieKeywords.indexOf("|")));
                movieKeywords = movieKeywords.substring(movieKeywords.indexOf("|")+1);
            }
            movieKeywordsList.add(movieKeywords);
            if(movieKeywordsList.contains(keyword)){
                results.add(movies.get(i));
            }
        }
        sortResults(results);
        for (int i = 0; i < results.size(); i++){
            String title = results.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = results.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        ArrayList<String> genres = new ArrayList<String>();
        for(int i = 0; i<movies.size();i++){
            String genresInMovie = movies.get(i).getGenres();
            while(genresInMovie.indexOf("|")!=-1){
                String a = genresInMovie.substring(0,genresInMovie.indexOf("|"));
                if(!genres.contains(a)){
                    genres.add(a);
                }
                genresInMovie = genresInMovie.substring(genresInMovie.indexOf("|")+1);
            }
            if(!genres.contains(genresInMovie)){
                genres.add(genresInMovie);
            }
        }
        Collections.sort(genres);
        for(int i = 0; i< genres.size();i++){
            int choiceNum = i+1;
            System.out.println("" + choiceNum + ". " + genres.get(i));
        }
        System.out.println("Select a genre to see all corresponding movies: ");
        System.out.print("Enter number: ");
        int choices = scanner.nextInt();
        scanner.nextLine();
        ArrayList<Movie> results = new ArrayList<Movie>();
        String b = genres.get(choices-1);
        for(int i = 0; i<movies.size();i++){
            if(movies.get(i).getGenres().contains(b)){
                results.add(movies.get(i));
            }
        }
        sortResults(results);
        for (int i = 0; i < results.size(); i++){
            String title = results.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = results.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void listHighestRated()
    {

        ArrayList<Movie> grapedumdum = new ArrayList<Movie>();
        sortResults2(movies);
        grapedumdum = movies;
        for(int i = grapedumdum.size()-1;i>49;i--){
            grapedumdum.remove(i);
        }


        for (int i = 0; i < grapedumdum.size(); i++){
            String title = grapedumdum.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title + " " + grapedumdum.get(choiceNum-1).getUserRating());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = grapedumdum.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }
    private void listHighestRevenue()
    {
        ArrayList<Movie> bea = new ArrayList<Movie>();
        sortResults3(movies);
        bea = movies;
        for(int i = bea.size()-1;i>49;i--){
            bea.remove(i);
        }


        for (int i = 0; i < bea.size(); i++){
            String title = bea.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title + " " + bea.get(choiceNum-1).getRevenue());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = bea.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}