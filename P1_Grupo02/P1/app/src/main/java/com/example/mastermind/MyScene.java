package com.example.mastermind;


    public class MyScene {
        private int x;
        private int y;
        private int radius;
        private int speed;

        private MyRenderClass renderClass;

        public MyScene(){
            this.x=100;
            this.y=0;
            this.radius = 100;
            this.speed = 150;
        }

        public void init(MyRenderClass renderClass){
            this.renderClass = renderClass;
        }

        public void update(double deltaTime){
            int maxX = this.renderClass.getWidth()-this.radius;
//            int maxY = this.renderClass.()-this.radius;

            this.x += this.speed * deltaTime;
            this.y += 1;
            while(this.x < this.radius || this.x > maxX) {
                // Vamos a pintar fuera de la pantalla. Rectificamos.
                if (this.x < this.radius) {
                    // Nos salimos por la izquierda. Rebotamos.
                    this.x = this.radius;
                    this.speed *= -1;
                } else if (this.x > maxX) {
                    // Nos salimos por la derecha. Rebotamos
                    this.x = 2 * maxX - this.x;
                    this.speed *= -1;
                }
                if (this.y> )
            }
        }

        public void render(){
            renderClass.renderCircle(this.x, this.y, this.radius);
        }
    }

