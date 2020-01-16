[![Release](https://img.shields.io/github/release/themsou/OpenMapGame.svg)](https://github.com/themsou/OpenMapGame/releases/)

## OpenMapGame

**Mon but pour cette application 3D (OpenGL avec LWJGL) est de faire un open map (une ville) dans laquelle on pourait se déplacer**

Cette application est seulement ma 2nde avec LWJGL donc j'espère que je pourait faire tout ce dont j'ai envie avec.
Je 

## Les APIs

J'ai utilisé l'API LWJGL 2.9.1 (LightWeight Java Game Library) qui est une adaptation de OpenGL pour Java.
Vous pourez facilement télécharger les dépendances sur internet.

À côté des dépendances sous forme de Jar se trouvent plusieurs natives, différentes selon les plateformes. J'ai donc intégré les natives dans le Jar avec ``JarSplice 0.40`` (.so pour Linux, .dll pour Windows et .dylib pour Mac).

Vous aurez bien sûr besoin d'avoir auparavant installé Java sur votre machine (1.8 conseillé).