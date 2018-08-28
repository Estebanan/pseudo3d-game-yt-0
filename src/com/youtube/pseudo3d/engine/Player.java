package com.youtube.pseudo3d.engine;

import java.awt.event.KeyEvent;

import com.youtube.pseudo3d.input.InputHandler;
import com.youtube.pseudo3d.resource.TextureHolder;
import com.youtube.pseudo3d.resource.TextureHolder.ID;
import com.youtube.pseudo3d.util.Vector2d;

public class Player {

	private Vector2d position;
	private Vector2d direction;

	private Camera camera;
	private TextureHolder textureHolder;

	private double initialFov;
	private double actualFov;
	private double fovAcceleration;

	private double initialMovementSpeed;
	private double initialRotationSpeed;

	private double actualMovementSpeed;
	private double actualRotationSpeed;

	private double movementAcceleration;

	public Player(Camera camera, TextureHolder textureHolder) {
		this.camera = camera;
		this.textureHolder = textureHolder;

		initInitialFields();
	}

	private void initInitialFields() {

		initialMovementSpeed = 25.0;
		initialRotationSpeed = 15.0;

		actualMovementSpeed = initialMovementSpeed;
		actualRotationSpeed = initialRotationSpeed;

		movementAcceleration = .99;

		initialFov = 1;
		actualFov = initialFov;
		fovAcceleration = .01;

		position = new Vector2d(22, 22);
		direction = new Vector2d(-1, 0);

		rotate(Math.PI / 2);
	}

	public void handleInput(double elapsed) {
		handleInputMovement(elapsed);
		handleInputSprinting(elapsed);
	}
	
	private void handleInputMovement(double elapsed) {
		double moveSpeed = actualMovementSpeed * elapsed;
		double rotationSpeed = actualRotationSpeed * elapsed;

		if (InputHandler.isKeyPressed(KeyEvent.VK_W))
			move(moveSpeed);

		if (InputHandler.isKeyPressed(KeyEvent.VK_S))
			move(-moveSpeed);

		if (InputHandler.isKeyPressed(KeyEvent.VK_D))
			rotate(-rotationSpeed);

		if (InputHandler.isKeyPressed(KeyEvent.VK_A))
			rotate(rotationSpeed);
	}
	
	private void handleInputSprinting(double elapsed) {
		// SPRINTING VELOCITY
		if (InputHandler.isKeyPressed(KeyEvent.VK_SHIFT)
				&& (InputHandler.isKeyPressed(KeyEvent.VK_W) && actualMovementSpeed < 3.4 * initialMovementSpeed)) {
			actualMovementSpeed += movementAcceleration;
		} else if (actualMovementSpeed > initialMovementSpeed) {
			actualMovementSpeed -= movementAcceleration;
		}

		// SPRINTING FOV
		if (InputHandler.isKeyPressed(KeyEvent.VK_SHIFT)
				&& (InputHandler.isKeyPressed(KeyEvent.VK_W) && actualFov < 2.0 * initialFov)) {
			actualFov += fovAcceleration;
		} else if (actualFov > initialFov) {
			actualFov -= 8 * fovAcceleration;
		}
	}

	public void move(double delta) {
		// ONLY MOVE IF THE CURRENT TILE IS 0XFF000000 - BLACK
		if (textureHolder.get(ID.TEST_MAP).getRGB((int) (position.x + direction.y * delta),
				(int) (position.y)) == 0xff000000)
			position.x += direction.y * delta;
		if (textureHolder.get(ID.TEST_MAP).getRGB((int) (position.x),
				(int) (position.y + direction.x * delta)) == 0xff000000)
			position.y += direction.x * delta;
	}
	
	public void rotate(double angle) {
		double oldDirX = direction.x;
		direction.x = direction.x * Math.cos(angle) - direction.y * Math.sin(angle);
		direction.y = oldDirX * Math.sin(angle) + direction.y * Math.cos(angle);
		double oldPlaneX = camera.getPlane().x;
		camera.getPlane().x = camera.getPlane().x * Math.cos(angle) - camera.getPlane().y * Math.sin(angle);
		camera.getPlane().y = oldPlaneX * Math.sin(angle) + camera.getPlane().y * Math.cos(angle);
	}

	public Vector2d getPosition() {
		return position;
	}

	public void setPosition(Vector2d position) {
		this.position = position;
	}

	public Vector2d getDirection() {
		return direction;
	}

	public void setDirection(Vector2d direction) {
		this.direction = direction;
	}

	public double getInitialFov() {
		return initialFov;
	}

	public void setInitialFov(double initialFov) {
		this.initialFov = initialFov;
	}

	public double getActualFov() {
		return actualFov;
	}

	public void setActualFov(double actualFov) {
		this.actualFov = actualFov;
	}

	public double getFovAcceleration() {
		return fovAcceleration;
	}

	public void setFovAcceleration(double fovAcceleration) {
		this.fovAcceleration = fovAcceleration;
	}

	public double getInitialMovementSpeed() {
		return initialMovementSpeed;
	}

	public void setInitialMovementSpeed(double initialMovementSpeed) {
		this.initialMovementSpeed = initialMovementSpeed;
	}

	public double getInitialRotationSpeed() {
		return initialRotationSpeed;
	}

	public void setInitialRotationSpeed(double initialRotationSpeed) {
		this.initialRotationSpeed = initialRotationSpeed;
	}

	public double getActualMovementSpeed() {
		return actualMovementSpeed;
	}

	public void setActualMovementSpeed(double actualMovementSpeed) {
		this.actualMovementSpeed = actualMovementSpeed;
	}

	public double getActualRotationSpeed() {
		return actualRotationSpeed;
	}

	public void setActualRotationSpeed(double actualRotationSpeed) {
		this.actualRotationSpeed = actualRotationSpeed;
	}

	public double getMovementAcceleration() {
		return movementAcceleration;
	}

	public void setMovementAcceleration(double movementAcceleration) {
		this.movementAcceleration = movementAcceleration;
	}
	
}
