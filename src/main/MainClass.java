package main;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

class MainClass{
    public static void main(String[] args){
        System.setProperty("sun.java2d.opengl", "true");
        new Game();

    }
}

