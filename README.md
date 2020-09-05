# Demo App with usage examples of the upcoming Jetpack Paging 3 library

The App uses the [The cat API](https://thecatapi.com/) to fetch an endless list of random cat images and lets the user select their favourites.
![](https://sebschaef.bitbucket.io/images/screen_cat.png)

## Explore tab
Displays an endless list of random cat images by using the API as the single `PagingSource`. The paged API data gets transformed into an UI model and displayed right away.
See: `CatRepository` -> `RandomCatImagesPagingSource` -> `ExploreViewModel` -> `ExploreFragment`

## Favourite tab
Displays the user's favourite cat images in a theoretically endless list. Here, the favourite image models are picked up through the API and getting cached via Room. The single source of truth is the database, but its contents are updated via API calls.
See: `CatRepository` -> `FavouriteCatImagesRemoteMediator` and `ImagesDao` -> `CatRepository` -> `FavouritesViewModel` -> `FavouritesFragment`

# Misc
* Loading and error states are observed and displayed
* "Retry" behaviour if an API call failed

To run and test it locally, get your own API key [here](https://thecatapi.com/signup) and put it into the respective property in the `Constants.kt`.