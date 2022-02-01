# Property Manager

My Property Manager project was used primarily as a learning oppurtunity to work on concurrently with my CodeCamp.  I was intent on using Angular and Spring Boot for this project, as I knew that's the best way to work.  The concept stems from my previous experence working at a real estate consultancy.  Users are able to add buildings, which can contain apartments, which in turn contain payments.

One of my main focuses for this project was learning how to make clear, concise code with a good user experience.  The frontend is written in Angular, which makes AJAX requests to APIs written in Spring Boot.  The AJAX requests allow the browser to avoid reloading the page when items are added, deleted, or edited.  The API then sends an HTTP response that includes the model and code confirming the request was succesful.  Angular then handles manipulating the view to update the information to match the backend without a second request.  The backend is largely a CRUD application with some functionality to allow users to retrieve apartments/payments by building/apartment.  The controller calls the corresponding service, which handles the business logic.  

<br/>

The main landing page shows a list of buildings and users have the ability to add new buildings, or edit and delete existing ones.  Clicking through on of the buildings leads to that specific building's list of apartments. 

![Image of building page](https://github.com/danielcslattery/PropertyManager/blob/a4dbc43085c2cc34fbfca8698a3132438f0d3eb5/Project%20Images/BuildingsPage.PNG)

<br/>

The apartment page has the same functionality as the building page.  In the image below, the user has clicked on the edit button, which opens a text box in line with the existing item.  The project is set up to not refresh when the user adds/deletes/edits an item for a more seamless experience. 

![Image of building page](https://github.com/danielcslattery/PropertyManager/blob/a4dbc43085c2cc34fbfca8698a3132438f0d3eb5/Project%20Images/ApartmentsPageWhileEditing.PNG)
