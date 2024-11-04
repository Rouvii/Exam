## TASK 3.3.3

### GET {{url}}/trip/
[
{

"id": 1,
"name": "Aalborg city tour",
"price": 3500.0,
"category": "CITY",
"startTime": [2025, 6, 1],
"endTime": [2025, 6, 10],
"startPosition": "Aalborg",
"guide": {
"id": 3,
"firstname": "Torben",
"lastname": "Turbo",
"email": "Turbo@torben.fr",
"phoneNumber": 12345678,
"yearsOfExperience": 10,
"trips": []
}
},
{
"id": 2,
"name": "Copenhagen city tour",
"price": 6700.0,
"category": "CITY",
"startTime": [2024, 12, 4],
"endTime": [2024, 12, 11],
"startPosition": "Tivoli",
"guide": {
"id": 3,
"firstname": "Torben",
"lastname": "Turbo",
"email": "Turbo@torben.fr",
"phoneNumber": 12345678,
"yearsOfExperience": 10,
"trips": []
}
},
{
"id": 3,
"name": "France forest hunt",
"price": 6700.0,
"category": "FOREST",
"startTime": [2025, 1, 13],
"endTime": [2025, 1, 15],
"startPosition": "Alsace",
"guide": {
"id": 3,
"firstname": "Torben",
"lastname": "Turbo",
"email": "Turbo@torben.fr",
"phoneNumber": 12345678,
"yearsOfExperience": 10,
"trips": []
}
},
{
"id": 4,
"name": "Berlin historical walk",
"price": 4500.0,
"category": "CITY",
"startTime": [2025, 4, 20],
"endTime": [2025, 4, 25],
"startPosition": "Berlin",
"guide": {
"id": 1,
"firstname": "Kristian",
"lastname": "Jensen",
"email": "KristianJensen@tour.dk",
"phoneNumber": 12345678,
"yearsOfExperience": 5,
"trips": []
}
},
{
"id": 5,
"name": "Spain",
"price": 5200.0,
"category": "BEACH",
"startTime": [2025, 2, 14],
"endTime": [2025, 2, 20],
"startPosition": "Tabernas Desert",
"guide": {
"id": 1,
"firstname": "Kristian",
"lastname": "Jensen",
"email": "KristianJensen@tour.dk",
"phoneNumber": 12345678,
"yearsOfExperience": 5,
"trips": []
}
},
{
"id": 6,
"name": "Norway fjord cruise",
"price": 8000.0,
"category": "SEA",
"startTime": [2025, 7, 5],
"endTime": [2025, 7, 12],
"startPosition": "Oslo Fjord",
"guide": {
"id": 1,
"firstname": "Kristian",
"lastname": "Jensen",
"email": "KristianJensen@tour.dk",
"phoneNumber": 12345678,
"yearsOfExperience": 5,
"trips": []
}
},
{
"id": 7,
"name": "Italy vineyard visit",
"price": 6000.0,
"category": "LAKE",
"startTime": [2025, 9, 10],
"endTime": [2025, 9, 15],
"startPosition": "Tuscany",
"guide": {
"id": 2,
"firstname": "Frederik",
"lastname": "Hansen",
"email": "Hansen@Mikkel.dk",
"phoneNumber": 87654321,
"yearsOfExperience": 3,
"trips": []
}
},
{
"id": 8,
"name": "Japan cherry blossom tour",
"price": 7200.0,
"category": "CITY",
"startTime": [2025, 3, 25],
"endTime": [2025, 4, 5],
"startPosition": "Tokyo",
"guide": {
"id": 2,
"firstname": "Frederik",
"lastname": "Hansen",
"email": "Hansen@Mikkel.dk",
"phoneNumber": 87654321,
"yearsOfExperience": 3,
"trips": []
}
},
{
"id": 9,
"name": "Iceland volcano expedition",
"price": 9500.0,
"category": "FOREST",
"startTime": [2025, 3, 1],
"endTime": [2025, 3, 7],
"startPosition": "Reykjanes Peninsula",
"guide": {
"id": 2,
"firstname": "Frederik",
"lastname": "Hansen",
"email": "Hansen@Mikkel.dk",
"phoneNumber": 87654321,
"yearsOfExperience": 3,
"trips": []
}
}
]

## TASK 3.3.3

### GET {{url}}/trip/2
GET http://localhost:7070/api/trip/2

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:04:06 GMT
Content-Type: application/json
Content-Length: 279

{
"id": 2,
"name": "Aalborg city tour",
"price": 3500.0,
"category": "CITY",
"startTime": [
2025,
6,
1
],
"endTime": [
2025,
6,
10
],
"startPosition": "Aalborg",
"guide": {
"id": 3,
"firstname": "Torben",
"lastname": "Turbo",
"email": "Turbo@torben.fr",
"phoneNumber": 12345678,
"yearsOfExperience": 10,
"trips": []
}
}

## TASK 3.3.3
### POST

#### COMMENT:
I have allowed the guide to null, 
as a trip can be created without and 
later added a guide


{
"id": 10,
"name": "New Trip",
"price": 5000.0,
"category": "CITY",
"startTime": [
2025,
6,
1
],
"endTime": [
2025,
6,
10
],
"startPosition": "New York",
"guide": null
}

## TASK 3.3.3
### update
### PUT {{url}}/trip/10

{
"id": 10,
"name": "Updated Trip",
"price": 6000.0,
"category": "CITY",
"startTime": [
2025,
6,
1
],
"endTime": [
2025,
6,
10
],
"startPosition": "New York",
"guide": null
}
{

## TASK 3.3.3

### DELETE {{url}}/trip/10


"status": 200,
"message": "Trip deleted",
"timestamp": "2024-11-04 11:21:03.813"
}

## TASK 3.3.3
### PUT {{url}}/trip/trips/10/guides/3

{
"status": 201,
"message": "Guide added to trip",
"timestamp": "2024-11-04 11:25:56.469"
}

## TASK 3.3.3
### Get trips by guide ID
GET {{url}}/trip/guides/3
[
{
"id": 2,
"name": "Copenhagen city tour",
"price": 6700.0,
"category": "CITY",
"startTime": [
2024,
12,
4
],
"endTime": [
2024,
12,
11
],
"startPosition": "Tivoli",
"guide": {
"id": 3,
"firstname": "Torben",
"lastname": "Turbo",
"email": "Turbo@torben.fr",
"phoneNumber": 12345678,
"yearsOfExperience": 10,
"trips": []
}
},
{
"id": 3,
"name": "Aalborg city tour",
"price": 3500.0,
"category": "CITY",
"startTime": [
2025,
6,
1
],
"endTime": [
2025,
6,
10
],
"startPosition": "Aalborg",
"guide": {
"id": 3,
"firstname": "Torben",
"lastname": "Turbo",
"email": "Turbo@torben.fr",
"phoneNumber": 12345678,
"yearsOfExperience": 10,
"trips": []
}
},
{
"id": 1,
"name": "France forest hunt",
"price": 6700.0,
"category": "FOREST",
"startTime": [
2025,
1,
13
],
"endTime": [
2025,
1,
15
],
"startPosition": "Alsace",
"guide": {
"id": 3,
"firstname": "Torben",
"lastname": "Turbo",
"email": "Turbo@torben.fr",
"phoneNumber": 12345678,
"yearsOfExperience": 10,
"trips": []
}
},
{
"id": 10,
"name": "New Trip",
"price": 5000.0,
"category": "CITY",
"startTime": [
2025,
6,
1
],
"endTime": [
2025,
6,
10
],
"startPosition": "New York",
"guide": {
"id": 3,
"firstname": "Torben",
"lastname": "Turbo",
"email": "Turbo@torben.fr",
"phoneNumber": 12345678,
"yearsOfExperience": 10,
"trips": []
}
}
]

## TASK 3.3.5

3.3.5 Theoretical question: Why do we suggest a PUT method for adding a guide to a trip instead of a POST method? Write the answer in your README.md file.

#### answer
Using put instead of post makes it idempotent, ensuring that the same request can be made multiple times without causing any changes to the resource

