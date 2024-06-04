javac -cp lib/jnativehook-2.2.2.jar;lib/jna.jar;lib/jna-platform.jar -d out src/main/java/KeyboardLayoutMaps.java src/main/java/KeyboardLayoutCorrector.java

cd out
jar cvf KeyboardLayoutCorrector.jar KeyboardLayoutMaps.class KeyboardLayoutCorrector.class
cd ..

jpackage --input out --name KeyboardLayoutCorrector --main-jar KeyboardLayoutCorrector.jar --main-class KeyboardLayoutCorrector --type exe
