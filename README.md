
# Tic-Tac-Toe_Game_Backend

Welcome to Tic Tac Toe REST API. Tic Tac Toe is a two-player game where opponents take turns marking an empty cell in a 3×3 grid with their respective symbols ("X" or "O"). The player who succeeds in placing three of their marks in a horizontal, vertical, or diagonal row is declared the winner. This API implementation utilizes Spring Boot for the backend, MySQL for data storage, and AWS for hosting.

## Features

- Player can register using unique name
- Registered players can start game
- Ongoing games can reset 
- Active players can play there turn

## API Endpoints (Postman collection):
- Postman Documentation URL: https://documenter.getpostman.com/view/25973908/2s9Ye8eubT
- This API host uses AWS:
```base  
     http://34.229.109.59/
```
## API Reference

#### - Register Players

```http
  POST /api/player/register
```

| Req. Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `playerName` | `string` | **Unique** Player names only can register. |

#### - Start Game

```http
  POST /api/game/start
```

| Req. Body | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `playerName`      | `string` | **Registered** players only can start. |

#### - Reset Game

```http
  PATCH /api/game/reset/{playerName}
```

| Req. Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `playerName`      | `string` | **Active** players only can restart. |
#### - Play a Turn

```http
  POST /api/game/playerTurn
```

| Req. Body | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `playerName`      | `string` | **Registered** players only can start. |
| `row` | `int`|Placed 'X' in player **Wanted**.|
|`column`|`int`|Placed 'X' in player **Wanted**.|

## Run Locally

Clone the project

```bash
  git clone https://github.com/KRMadhusanka99/Tic-Tac-Toe_Game_Backend.git
```

Go to the project directory

```bash
  cd my-project
```

Start as normal spring boot application. 

## Running Tests

Use Postman to check API Endpoints. All Endpoints' description has mention above.
## Support
If any case the host services not working properly you can download my code from GitHub using the above-mentioned URL, extract and run it as a normal spring boot app. Then for API calling use http://localhost:8080 or the port that your computer has given rather than using the IP address I mentioned above.

Furthermore, I used a hosted database as well, so if any case the database not working as expected, you need to change the application.properties class to your local database configurations. I already commented on code lines for your easiness.
