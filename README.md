# Android_MovieDB
This Application examines creating and managing SQLiteDatabasesb and Http request calls. 
The application is a movie app that shows the now-playing movies and allows the user to sort the movies by different criteria. 
The requirements are:

1. This homework is based on the API from https://www.themoviedb.org/.  API documentation can be found: https://developers.themoviedb.org/3 
    Try to get the now-playing movie list from https://developers.themoviedb.org/3/movies/get-now-playing. The results are listed in multiple pages. 
    For this applicatoin, only the moview on the first page are shown (with page=1). A movie JSON object looks like this:
    {  
   "vote_count":902,
   "id":166426,
   "video":false,
   "vote_average":6.5,
   "title":"Pirates of the Caribbean: Dead Men Tell No Tales",
   "popularity":213.468872,
   "poster_path":"\/xbpSDU3p7YUGlu9Mr6Egg2Vweto.jpg",
   "original_language":"en",
   "original_title":"Pirates of the Caribbean: Dead Men Tell No Tales",
   "genre_ids":[  
      28,
      12,
      35,
      14
   ],
   "backdrop_path":"\/3DVKG54lqYbdh8RNylXeCf4MBPw.jpg",
   "adult":false,
   "overview":"Captain Jack Sparrow searches for the trident of Poseidon while being pursued by an undead sea captain and his crew.",
   "release_date":"2017-05-23"
  }
  
 2. The application must have two screens: a list screen showing the now-playing movie list and a detail screen showing information 
    about the selected movie. The user can navigate to the detail screen by clicking on the movie item. The user can go back to 
    list screen by clicking back button or some other button added by yourself. You can use either two Activities or two Fragments to 
    implement the two-screen design.
  
  
3. On the list screen, there must be two sections:
   Section 1 provides refreshing and rank-by functions. There must be a button allowing the user refresh the list. 
   Whenever the user click the refresh button, the app downloads the latest list from the API. User must be able to select the ranking 
   criteria of either title, vote(use vote_average) or popularity. These information can be retrieved from the JSON. 

   Section 2 shows a list of now-playing movies. The returned movies are showed in this section. For each movie, 
   the app must show a thumbnail poster picture (low resolution), the title, vote/popularity score and release date. 
   The user must be able to scroll up and down to look at all the now-playing movies.

   When the user select a ranking criteria, the list of movies are reordered according to the rank criteria. For titles, 
   sort the movie alphabetically. When selecting vote and popularity, sort the movies by descending order of vote or popularity.
   
4. The detail screen shows information about the selected movie. On the detail screen, the app must show a high-resolution poster 
   picture, the title, popularity, vote, and overview of the movie. You can get the picture with the poster_path and 
   the URI “image.tmdb.org”. An example to retrieve poster picture: http://image.tmdb.org/t/p/w185/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg. 
   You can dynamically download the picture from the tmdb. But they must appear in a reasonable time.
   
 
5. The detail screen must provide a "like" function. User can press a like button to mark favorite movies on the detail screen. 
   The user must be able to cancel the like. In the list screen, add a widget to allow the user to filter other movies and only see all 
   the favorite movies.
   
6. The now-playing movie list must be initially downloaded from the API after clicking the refresh button. Then the movie information 
    must be stored in the SQLiteDatabase. The sorted movie list must be directly retrieved from the SQLiteDatabase with SQL queries. 
    You can implement the app only in portrait mode. But the movie information must exist when the user leave and come back to the app.



SCREENSHOTS:

Movies List (Left). Favorited Movie in Details Screen (Right).

![Image](https://github.com/wesh95/Android_MovieDB/blob/master/MovieDB_Screenshots/hw3_1.JPG)
![Image](https://github.com/wesh95/Android_MovieDB/blob/master/MovieDB_Screenshots/hw3_3.JPG)

Movies List filtered for favorited Movies (Left). Landscape View (Right).

![Image](https://github.com/wesh95/Android_MovieDB/blob/master/MovieDB_Screenshots/hw3_4.JPG)
![Image](https://github.com/wesh95/Android_MovieDB/blob/master/MovieDB_Screenshots/hw3_2.JPG)
