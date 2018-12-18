package com.snake.rodion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.snake.rodion.Constants;
import com.snake.rodion.MainGame;
import com.snake.rodion.SettingFlag;
import com.snake.rodion.entities.BoardEntity;
import com.snake.rodion.entities.FoodEntity;
import com.snake.rodion.entities.SnakeEntity;
import com.snake.rodion.inputs.GameInput;
import com.snake.rodion.snakeWorld.Action;
import com.snake.rodion.snakeWorld.Direction;
import com.snake.rodion.snakeWorld.Frame;
import com.snake.rodion.snakeWorld.Node;
import com.snake.rodion.snakeWorld.World;
import com.snake.rodion.utilities.Utilities;

import java.util.ArrayList;
import java.util.EnumSet;

import static com.snake.rodion.Constants.BANNER_HEIGHT;
import static com.snake.rodion.Constants.HEIGHT;
import static com.snake.rodion.Constants.WIDTH;
import static com.snake.rodion.snakeWorld.Direction.ANYWHERE;
import static com.snake.rodion.snakeWorld.Direction.DOWN;
import static com.snake.rodion.snakeWorld.Direction.LEFT;
import static com.snake.rodion.snakeWorld.Direction.RIGHT;
import static com.snake.rodion.snakeWorld.Direction.UP;

public class GameScreen extends BaseScreen {


    EnumSet<SettingFlag> settings;
    private Stage stage;
    private World world;
    private SnakeEntity snakeEntity;
    private FoodEntity foodEntity;
    private BoardEntity boardEntity;
    private Texture textureScore;
    private Texture textureHighScore;
    private Texture textureRestart;
    private Texture textureClose;
    private Texture textureSoundOn;
    private Texture textureSoundOff;
    private Texture textureBackGround;
    private Texture textureZero;
    private Texture textureOne;
    private Texture textureTwo;
    private Texture textureThree;
    private Texture textureFour;
    private Texture textureFive;
    private Texture textureSix;
    private Texture textureSeven;
    private Texture textureEight;
    private Texture textureNine;
    private Image imageZero;
    private Image imageOne;
    private Image imageTwo;
    private Image imageThree;
    private Image imageFour;
    private Image imageFive;
    private Image imageSix;
    private Image imageSeven;
    private Image imageEight;
    private Image imageNine;
    private Image imageFirstSlotScore;
    private Image imageSecondSlotScore;
    private Image imageThirdSlotScore;
    private Image imageFirstSlotHighScore;
    private Image imageSecondSlotHighScore;
    private Image imageThirdSlotHighScore;
    private Sound soundUp;
    private Sound soundDown;
    private Sound soundLeft;
    private Sound soundRight;
    private Sound soundEat;
    private Sound soundCrash;
    private GameInput gameInput;
    //private Texture backgroundTexture;
    private float elapsedTime;
    private int score;
    private int highScore;

    public GameScreen(MainGame mainGame) {
        super(mainGame);


        settings = EnumSet.noneOf(SettingFlag.class);
        settings.add(SettingFlag.PAUSE);
        score = 0;
        stage = new Stage(new FitViewport(720, 1280));
        textureBackGround = new Texture(Gdx.files.internal("background.png"));
        textureClose = new Texture(Gdx.files.internal("close.png"));
        textureSoundOff = new Texture(Gdx.files.internal("soundOff.png"));
        textureSoundOn = new Texture(Gdx.files.internal("soundOn.png"));
        textureScore = new Texture(Gdx.files.internal("score.png"));
        textureHighScore = new Texture(Gdx.files.internal("highScore.png"));
        textureRestart = new Texture(Gdx.files.internal("restart.png"));

        textureZero = new Texture(Gdx.files.internal("0.png"));
        textureOne = new Texture(Gdx.files.internal("1.png"));
        textureTwo = new Texture(Gdx.files.internal("2.png"));
        textureThree = new Texture(Gdx.files.internal("3.png"));
        textureFour = new Texture(Gdx.files.internal("4.png"));
        textureFive = new Texture(Gdx.files.internal("5.png"));
        textureSix = new Texture(Gdx.files.internal("6.png"));
        textureSeven = new Texture(Gdx.files.internal("7.png"));
        textureEight = new Texture(Gdx.files.internal("8.png"));
        textureNine = new Texture(Gdx.files.internal("9.png"));

        soundUp = Gdx.audio.newSound(Gdx.files.internal("up.wav"));
        soundDown = Gdx.audio.newSound(Gdx.files.internal("down.wav"));
        soundLeft = Gdx.audio.newSound(Gdx.files.internal("left.wav"));
        soundRight = Gdx.audio.newSound(Gdx.files.internal("right.wav"));
        soundEat = Gdx.audio.newSound(Gdx.files.internal("eat.wav"));
        soundCrash = Gdx.audio.newSound(Gdx.files.internal("crash.wav"));

        ArrayList<Node> initSnakeBody = new ArrayList<Node>();
        initSnakeBody.add(new Node(1, 1));
        initSnakeBody.add(new Node(1, 2));
        world = new World(initSnakeBody, new Frame(new Node(0, 0), new Node(WIDTH, HEIGHT)), new Node(3, 3), UP);
        snakeEntity = new SnakeEntity(world.getSnake());
        foodEntity = new FoodEntity(world.getFood());
        boardEntity = new BoardEntity();

        gameInput = new GameInput(this);
        Table table = new Table();
        table.setFillParent(true);
        final ImageButton imageButtonSound = new ImageButton(new TextureRegionDrawable(new TextureRegion(textureSoundOn)),
                new TextureRegionDrawable(new TextureRegion(textureSoundOff)),
                new TextureRegionDrawable(new TextureRegion(textureSoundOff))
        );
        imageButtonSound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!settings.contains(SettingFlag.MUTE)) {
                    settings.add(SettingFlag.MUTE);
                    imageButtonSound.setChecked(true);
                } else {
                    settings.remove(SettingFlag.MUTE);
                    imageButtonSound.setChecked(false);
                }
            }
        });

        ImageButton imageButtonRestart = new ImageButton(new TextureRegionDrawable(new TextureRegion(textureRestart)));
        imageButtonRestart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ArrayList<Node> initSnakeBody = new ArrayList<Node>();
                initSnakeBody.add(new Node(1, 1));
                initSnakeBody.add(new Node(1, 2));
                world.getSnake().setBody(initSnakeBody);
                world.getSnake().setVitals(true);
                world.getFood().setPosition(new Node(3, 3));
                world.setDirection(UP);
                settings.add(SettingFlag.PAUSE);
                score = 0;
                setDigit(imageFirstSlotScore, 0);
                setDigit(imageSecondSlotScore, 0);
                setDigit(imageThirdSlotScore, 0);
            }
        });

        imageZero = new Image(textureZero);
        imageOne = new Image(textureOne);
        imageTwo = new Image(textureTwo);
        imageThree = new Image(textureThree);
        imageFour = new Image(textureFour);
        imageFive = new Image(textureFive);
        imageSix = new Image(textureSix);
        imageSeven = new Image(textureSeven);
        imageEight = new Image(textureEight);
        imageNine = new Image(textureNine);

        imageFirstSlotScore = new Image();
        imageSecondSlotScore = new Image();
        imageThirdSlotScore = new Image();

        setDigit(imageFirstSlotScore, 0);
        setDigit(imageSecondSlotScore, 0);
        setDigit(imageThirdSlotScore, 0);

        imageFirstSlotHighScore = new Image();
        imageSecondSlotHighScore = new Image();
        imageThirdSlotHighScore = new Image();


        Preferences scorePreferences = Gdx.app.getPreferences("High Score Preferences");
        highScore = scorePreferences.getInteger("High Score", 0);

        setDigit(imageFirstSlotHighScore, 0);
        setDigit(imageSecondSlotHighScore, 0);
        setDigit(imageThirdSlotHighScore, 0);

        ArrayList<Integer> digits = Utilities.number2Array(highScore);
        if (digits.size() > 0)
            setDigit(imageThirdSlotHighScore, digits.get(0));
        if (digits.size() > 1)
            setDigit(imageSecondSlotHighScore, digits.get(1));
        if (digits.size() > 2)
            setDigit(imageFirstSlotHighScore, digits.get(2));

        imageButtonSound.setChecked(false);
        table.add(new Image(textureScore));
        table.add(imageFirstSlotScore);
        table.add(imageSecondSlotScore);
        table.add(imageThirdSlotScore).padRight(64);

        table.add(new Image(textureHighScore));
        table.add(imageFirstSlotHighScore);
        table.add(imageSecondSlotHighScore);
        table.add(imageThirdSlotHighScore).padRight(64);


        table.add(imageButtonSound).padRight(64);
        table.add(imageButtonRestart).height(BANNER_HEIGHT);
        table.row();
        table.add(new Image(textureBackGround)).colspan(10);
        table.addActor(boardEntity);
        table.addActor(snakeEntity);
        table.addActor(foodEntity);
        //table.add(verticalGroup);

        stage.addActor(table);
        elapsedTime = 0.f;
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new GestureDetector(gameInput));
        Gdx.input.setInputProcessor(multiplexer);
    }


    public void onAction(Direction inputDirection) {
        if (world.getSnake().getVitals()) {
            if (settings.contains(SettingFlag.PAUSE)) {
                settings.remove(SettingFlag.PAUSE);
                elapsedTime = 0;
            }
            Direction outputDirection = ANYWHERE;
            switch (inputDirection) {
                case UP:
                    outputDirection = world.move(DOWN);
                    break;
                case DOWN:
                    outputDirection = world.move(UP);
                    break;
                case RIGHT:
                    outputDirection = world.move(RIGHT);
                    break;
                case LEFT:
                    outputDirection = world.move(LEFT);
                    break;
            }
            if (!settings.contains(SettingFlag.MUTE))
                switch (outputDirection) {
                    case UP:
                        soundUp.play();
                        break;
                    case DOWN:
                        soundDown.play();
                        break;
                    case RIGHT:
                        soundRight.play();
                        break;
                    case LEFT:
                        soundLeft.play();
                        break;
                }
        }
    }

    @Override
    public void render(float delta) {


        if (!settings.contains(SettingFlag.PAUSE)) {

            elapsedTime += delta;
            if (elapsedTime > Constants.TIME_PER_MOVE) {
                elapsedTime = 0;
            }
            if (elapsedTime == 0) {
                EnumSet<Action> actions = world.step();
                if (actions.contains(Action.EAT)) {
                    score = world.getSnake().getSize() - 2;

                    ArrayList<Integer> digits = Utilities.number2Array(score);
                    if (digits.size() > 0)
                        setDigit(imageThirdSlotScore, digits.get(0));
                    if (digits.size() > 1)
                        setDigit(imageSecondSlotScore, digits.get(1));
                    if (digits.size() > 2)
                        setDigit(imageFirstSlotScore, digits.get(2));
                    if (!settings.contains(SettingFlag.MUTE))
                        soundEat.play();

                    Preferences scorePreferences = Gdx.app.getPreferences("High Score Preferences");
                    int storedHighScore = scorePreferences.getInteger("High Score", 0);
                    if (score > storedHighScore) {
                        highScore = score;
                        scorePreferences.putInteger("High Score", highScore);
                        scorePreferences.flush();
                    }

                    digits = Utilities.number2Array(highScore);
                    if (digits.size() > 0)
                        setDigit(imageThirdSlotHighScore, digits.get(0));
                    if (digits.size() > 1)
                        setDigit(imageSecondSlotHighScore, digits.get(1));
                    if (digits.size() > 2)
                        setDigit(imageFirstSlotHighScore, digits.get(2));

                }
                if (actions.contains(Action.CRASH)) {
                    if (!settings.contains(SettingFlag.MUTE))
                        soundCrash.play();
                    settings.add(SettingFlag.PAUSE);
                }
            }

        }
        if (world.getSnake().getVitals()) {
            Gdx.gl.glClearColor(68.f / 255, 66.f / 255, 66.f / 255, 1.f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        snakeEntity.dispose();
        foodEntity.dispose();
        textureScore.dispose();
        textureHighScore.dispose();
        textureRestart.dispose();
        textureClose.dispose();
        textureSoundOn.dispose();
        textureSoundOff.dispose();
        textureBackGround.dispose();
        soundUp.dispose();
        soundDown.dispose();
        soundLeft.dispose();
        soundRight.dispose();
        soundEat.dispose();
        soundCrash.dispose();
        textureZero.dispose();
        textureOne.dispose();
        textureTwo.dispose();
        textureThree.dispose();
        textureFour.dispose();
        textureFive.dispose();
        textureSix.dispose();
        textureSeven.dispose();
        textureEight.dispose();
        textureNine.dispose();
        stage.dispose();
    }

    private void setDigit(Image image, int digit) {
        switch (digit) {
            case 0:
                image.setDrawable(imageZero.getDrawable());
                break;
            case 1:
                image.setDrawable(imageOne.getDrawable());
                break;
            case 2:
                image.setDrawable(imageTwo.getDrawable());
                break;
            case 3:
                image.setDrawable(imageThree.getDrawable());
                break;
            case 4:
                image.setDrawable(imageFour.getDrawable());
                break;

            case 5:
                image.setDrawable(imageFive.getDrawable());
                break;
            case 6:
                image.setDrawable(imageSix.getDrawable());
                break;

            case 7:
                image.setDrawable(imageSeven.getDrawable());
                break;
            case 8:
                image.setDrawable(imageEight.getDrawable());
                break;
            case 9:
                image.setDrawable(imageNine.getDrawable());
                break;
            default:
                image.setDrawable(imageZero.getDrawable());
        }
    }
}