// maxX = 1612, maxY = 720

int screenWidth = 1612; // set to your maxX
int screenHeight = 720; // set to your maxY

// Left paddle X and Y position
int leftPaddleX = 0;
int leftPaddleY = 0;

// Left paddle width and height
int leftPaddleWidth = 30;
int leftPaddleHeight = 100;

// Right paddle X and Y Position
int rightPaddleX = 1500;
int rightPaddleY = 600;

// Right paddle Width and Height
int rightPaddleWidth = 30;
int rightPaddleHeight = 100;

// ballX and ballY position
int ballX = 806;
int ballY = 360;

// Ball Width and Height
int ballWidth = 60;
int ballHeight = 60;

int xSpeed = 5; // Ball's horizontal speed
int ySpeed = 5; // Ball's vertical speed

int radius = 30; // Since half the width or height of the ball is the radius

// Player text size
int scoreSize = 24;

// Player 1 score X and Y position
int playerOneX = 600;
int playerOneY = 400;

// Player 2 score X and Y position
int playerTwoX = 900;
int playerTwoY = 420;

boolean gameOn = false;

// Initialize player scores
int playerOneScore = 0;
int playerTwoScore = 0;

void setup() // runs once
{
  fullScreen(); // Sets the program to run in full screen mode
}

void draw() // runs forever
{
  background(0);

  // Draw Left Paddle
  fill(0, 255, 0);
  stroke(255, 0, 0);
  rect(leftPaddleX, leftPaddleY, leftPaddleWidth, leftPaddleHeight);

  // Draw Player One Score
  fill(255);
  textSize(scoreSize);
  text(playerOneScore, playerOneX, playerOneY);

  // Draw Ball
  fill(255);
  ellipse(ballX, ballY, ballWidth, ballHeight);

  // Draw Player Two Score
  fill(255);
  textSize(scoreSize);
  text(playerTwoScore, playerTwoX, playerTwoY);

  // Draw Right Paddle
  fill(0, 255, 0);
  stroke(255, 0, 0);
  rect(rightPaddleX, rightPaddleY, rightPaddleWidth, rightPaddleHeight);

  // Check if mouse is pressed, set gameOn true
  if (mousePressed) {
    gameOn = true;
  }

  // Move ball if game is on
  if (gameOn) {
    ballX += xSpeed;
    ballY += ySpeed;
  }

  // Check for collision with left paddle
  if (doesOverlap(leftPaddleX, leftPaddleY, leftPaddleWidth, leftPaddleHeight, ballX, ballY, radius)) {
    xSpeed *= -1; // Reverse horizontal direction of the ball
  }

  // Check for collision with right paddle
  if (doesOverlap(rightPaddleX, rightPaddleY, rightPaddleWidth, rightPaddleHeight, ballX, ballY, radius)) {
    xSpeed *= -1; // Reverse horizontal direction of the ball
  }

  // Check if ball hits left or right walls
  if (ballX - radius <= 0) {
    // Ball hits left wall
    playerTwoScore++; // Increment right player's score
    gameOn = false; // Turn off game
    resetBall(); // Reset ball to the center
  } else if (ballX + radius >= screenWidth) {
    // Ball hits right wall
    playerOneScore++; // Increment left player's score
    gameOn = false; // Turn off game
    resetBall(); // Reset ball to the center
  }

  // Check if ball hits top or bottom walls
  if ((ballY - radius <= 0) || (ballY + radius) >= screenHeight) {
    ySpeed *= -1; // Reverse vertical direction
  }

  // Move paddles based on touch
  movePaddles();
}

// Reset ball to center of the screen
void resetBall() {
  ballX = screenWidth / 2;
  ballY = screenHeight / 2;
  xSpeed = 5;
  ySpeed = 5;
}

// Function to move paddles based on touch position
void movePaddles() {
  // Move left paddle if left side of screen is touched
  if (mouseX < screenWidth / 2) {
    leftPaddleY = constrain(mouseY, 0, screenHeight - leftPaddleHeight);
  }
  // Move right paddle if right side of screen is touched
  if (mouseX > screenWidth / 2) {
    rightPaddleY = constrain(mouseY, 0, screenHeight - rightPaddleHeight);
  }
}

// Checks if the ball overlaps with a paddle
boolean doesOverlap(int xPaddle, int yPaddle, int widthPaddle, int heightPaddle, int xBall, int yBall, int radius) {
  int ballLeftEdge = xBall - radius;
  int ballBottomEdge = yBall + radius;
  int ballRightEdge = xBall + radius;
  int ballTopEdge = yBall - radius;

  int paddleLeftEdge = xPaddle;
  int paddleBottomEdge = yPaddle + heightPaddle;
  int paddleRightEdge = xPaddle + widthPaddle;
  int paddleTopEdge = yPaddle;

  return ballBottomEdge >= paddleTopEdge &&
         ballTopEdge <= paddleBottomEdge &&
         ballLeftEdge <= paddleRightEdge &&
         ballRightEdge >= paddleLeftEdge;
}