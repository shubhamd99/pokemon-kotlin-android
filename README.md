### Pokemon App With Kotlin Android

![screenshot](https://i.imgur.com/6wUNWXq.png)

##### Fragments

A Fragment represents a reusable portion of your app's UI. A fragment defines and manages its own layout, has its own lifecycle, and can handle its own input events. Fragments cannot live on their own--they must be hosted by an activity or another fragment. The fragment’s view hierarchy becomes part of, or attaches to, the host’s view hierarchy.

##### Modularity

Fragments introduce modularity and reusability into your activity’s UI by allowing you to divide the UI into discrete chunks. Activities are an ideal place to put global elements around your app's user interface, such as a navigation drawer. Conversely, fragments are better suited to define and manage the UI of a single screen or portion of a screen.

Consider an app that responds to various screen sizes. On larger screens, the app should display a static navigation drawer and a list in a grid layout. On smaller screens, the app should display a bottom navigation bar and a list in a linear layout. Managing all of these variations in the activity can be unwieldy. Separating the navigation elements from the content can make this process more manageable. The activity is then responsible for displaying the correct navigation UI while the fragment displays the list with the proper layout.

![fragments](https://developer.android.com/images/guide/fragments/fragment-screen-sizes.png)

##### Types of permissions

1. Install-time permissions - Install-time permissions give your app limited access to restricted data, and they allow your app to perform restricted actions that minimally affect the system or other apps. When you declare install-time permissions in your app, the system automatically grants your app the permissions when the user installs your app.

2. Dangerous - (Runtime permissions), also known as dangerous permissions, give your app additional access to restricted data, and they allow your app to perform restricted actions that more substantially affect the system and other apps. Therefore, you need to request runtime permissions in your app before you can access the restricted data or perform restricted actions. When your app requests a runtime permission, the system presents a runtime permission prompt as shown in Figure.

![ask permission](https://developer.android.com/images/training/permissions/runtime.svg)