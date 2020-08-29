First of all, thank you again, for inviting me to the coding challenge here! Was actually fun to
solve this task. Mostly, because if something didn't work on the first try, I had at least some cute
cat pictures to stare at.

Architecture:
I decided to use an MVI'ish architecture pattern here: Following the state/event flow for the
loading, error display and fav'ing unfav'ing images. However I also use an additional state only
for the images list here. So, kind of a mixture between MVI and MVVM. The reason for my decision
was the usage of the paging library, which recommends this additional state flow for the list.
For providing the database access and repository singletons, I used Koin for dependency injection.

Data flow:
The Repository provides functions to retrieve the data from the API and local database. It uses a
Retrofit service (with OkHttp) to fetch the JSON Strings from the cat API and Moshi to transform
them into our network model objects. These network model objects are then mapped by our
ReponseMappers to our persistence models.
- In the explore tab, we use these resulting persistence models directly to display them in the list.
- In the favourite tab, we store them via Room in the local database and load them from there to
  provide the user a caching mechanism. So they can watch them even when the device is offline.

UI:
Also a pretty standard setup here, with a bottom bar to switch between two Fragments. The switching
logic is done with the Jetpack navigation library. Each fragment contains a SwipeRefreshLayout with
a RecyclerView inside.
The unlimited scroll function and page loading is done with the Jetpack paging library. It handles
the page refreshing, page appending, page caching and provides loading states etc. with only a
small configuration provided.
To display the images I used Glide, which also caches the images locally.

Tests:
I added some unit tests for the two ResponseMapper extension functions.

What could be improved:
- More tests! Mocking database and network results to also test the logic inside the repository. Maybe
  even some UI tests, which could be rather easy to implement thanks to the MVI architecture.
- UI representation for fav state changes: At the moment, it could be that the user clicks on the
  star to fav an image and their network is slow. They won't see that the application is actually
  doing something. A solution would be to locally adjust the fav state to give directly a UI
  feedback. If the network call fails, we simply reset that state.
- Specific error messages. At the moment I implemented two generic ones, but a good error message
  should provide more specific information and a possible solution. E.g. "It looks like you are
  offline. Please check your connection and try it again."