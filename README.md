# Android Take Home Test
## Assignment:

Your task is to build an Android Application that can convert Foreign Currency using this free API: [Fixer](https://fixer.io/documentation). The application will contain 2 screens:

### 1st screen: 

Will load the [Supported Symbols Endpoint](https://fixer.io/documentation#supportedsymbols) and display a vertical list of all supported countries along with their 3-letter currency symbol.

```
A list item should look like this 
-------------------------
|  AMD |  Armenian Dram |
-------------------------
```

When the user clicks on a list item (that is selecting a currency symbol), we will open our 2nd screen and use the currency symbol as the "base" currency to convert to multiple other currencies.

> NOTE: FIXER Free Plan will only accept EUR as the selected base currency, so the candidate should use EUR to test for the happy path. When selecting any other currency symbol, the api will send back such an error `{"success":false,"error":{"code":105,"type":"base_currency_access_restricted"}}`. Please parse any error type and display it plainly in an Alert Dialog via DialogFragment.   

> Senior Level: implement a search text box on the top of the list to quickly search for the base currency.


### 2nd screen contains: 
+ 1 EditText will always be fixed at the top that should only accept numbers, in which the user can enter the amount that he/she wants to convert to.
+ 1 RecyclerView to display all converted currency that are returned by the [Latest Rates Endpoint](https://fixer.io/documentation#latestrates). The list should have the 3-letter currency on the left following by the converted amount which should be rounded to the decimal places. 

```
If a user enters 3 in the EditText for EUR as Base Currency
A list item should look like this 
-------------------------
|  USD |  3.59          |
-------------------------
```
> Senior Level: display the currency symbol next to the amount for all currencies. For example: USD would be $3.59


### The application must follow:
- Best Android Development Practice (The cleaner your code is, the easier you can write unit tests)
- Error handling
- MVVM Arch
- All codes must be in KOTLIN
- Use Retrofit with either RxJava or Coroutines Flow.
- *Unit Test to cover your code as much as possible. (Perhaps, using [Robolectric](http://robolectric.org/) to test your Android code)*

> Senior Level: also implementing a simple cache (can just use SharedPreferences) that expired in 30 minutes.

# Evaluation:
+ We will pay particular attention to the way the code is organized, and to the overall readability
+ Unit tests will be GREATLY appreciated. Keep in mind that we grade the project on how well the unit test coverage is.
+ Design will be ignored, however usability and accessibility will be taken into consideration
+ Remember to update this README with instructions on how to install, run and test your application
+ We hope that you can complete the assignment within 2-3 hours but don't set any time constraints
+ Please reach out per email or by opening an issue if anything is unclear or blocking you

> BEST OF LUCK 

# Dev Notes
+ A device or emulator targeting minSdk 26 is required

## How to run app & test
+ You will need to create 'keystore.properties' and place it in the root directory. The file contains 3 keys (same value)
    + fixer.debug.key
    + fixer.staging.key
    + fixer.release.key
+ Optionally create a release signing key of your choice to run the release configuration

## Future Improvement
+ Code Structuring:
  + ProtoDatastore for caching
  + Room for caching
  + Fastlane for automation and instrumentation
  + Layout tests [specific to Jetpack Compose](https://developer.android.com/jetpack/compose/testing)
+ Refactoring:
  + I started out using [Kotlin serialization](https://github.com/JakeWharton/retrofit2-kotlinx-serialization-converter)
    but ran into problems with the converter factory for long to Instant so opted for Gson support instead
+ Additional Features:
  + Support additional endpoints
  + Better message handling (please ask me about this one)
