*this is Todo list*
### Api url format ###

* http://api.themoviedb.org/3   +
* /discover/movie? +
* sort_by={popularity.desc } +
* &language = [ISO 639-1 code.] +

>can be set in setting, default is en  //reference: https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes

* &include_adult = [false/true] +
* &page = [Minimum 1, maximum 1000.]  + 
 
>note: 20 items per page

* &api_key=[YOUR API KEY]

### Example ###

http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=5675845e8cb1ba7adc1776ef9131fec7&include_adult=true


### A wrapper for this api ###

TmdbMovies movies = new TmdbApi("<apikey>").getMovies();
MovieDb movie = movies.getMovie(5353, "en");

*do it first by parsing json result*
*and then try to use the wrapper*


### Todo List ###

1. get the ui done with mock data(image and text)
2. replace the mock data
    * using moviedb api to get the actual data
    * load image with picasso liabray
3. implement the sort in the setting
    *sort by what*
    * popularity
    * release_date
    * revenue
    * primary_release_date
    * original_title
    * vote_average
    * vote_count
    *ascending or descending by add .asc or .desc to the above item*
    * asc or desc

>default is sort by popularity and desc

4. implement the seetting in the drawer
    * include adult?
    * how many page to get ? 1~1000 *with hint : 20 item per page*
    * language *chinese, english, japanese*



### Implement later ###

1. filter by
	* certification_country
	* release year (=, >=, <= and etc.)
	* vote count
	* vote_average
	* with_cast
	* with_crew
	* with_companies
	* with_genres
	* with_keywords
	* with_people

2. change the sort into the floating action button
