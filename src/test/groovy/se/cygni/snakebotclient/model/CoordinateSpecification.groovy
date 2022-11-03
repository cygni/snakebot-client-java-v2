package se.cygni.snakebotclient.model

import se.cygni.snakebotclient.model.enums.Direction
import spock.lang.Shared
import spock.lang.Specification

class CoordinateSpecification extends Specification {

    @Shared
    Random random = new Random();

    def "isWithinSquare"(int x, int y){
        given:
        var c1 = new Coordinate(x,y)

        when:
        var insideSquare = c1.isWithinSquare(topLeftBound, bottomRightBound)

        then:
        insideSquare == expected

        where:
        topLeftBound = new Coordinate(0,9)
        bottomRightBound = new Coordinate(9, 0)

        x  | y  || expected
        0  | 0  || true
        0  | 9  || true
        9  | 0  || true
        9  | 9  || true
        -1 | 0  || false
        0  | 10 || false
        10 | 0  || false
        10 | 10 || false


    }

    def "isOutOfBounds"(int x, int y, int mapWidth, int mapHeight) {
        given:
        var c1 = new Coordinate(x,y)

        when:
        var outOfBound = c1.isOutOfBounds(mapWidth, mapHeight)

        then:
        outOfBound == expected

        where:
        mapWidth = 10
        mapHeight = 10

        x  | y  || expected
        0  | 0  || false
        0  | 9  || false
        9  | 0  || false
        9  | 9  || false
        -1 | 0  || true
        0  | 10 || true
        10 | 0  || true
        10 | 10 || true
    }

    def "getEuclidianDistance"(int x1, int x2, int y1, int y2) {
        given:
        var c1 = new Coordinate(x1, y1)
        var c2 = new Coordinate(x2, y2)

        when:
        var euclideanDistance = c1.getEuclidianDistance(c2)

        then:
        euclideanDistance == expected

        where:
        x1 | y1 | x2 | y2 || expected
        2  | 2  | 2  | 3  || 1.0
        2  | 2  | 2  | 7  || 5.0
        2  | 2  | 7  | 2  || 5.0
        2  | 2  | 6  | 6  || Math.sqrt(32)
        2  | 2  | 3  | 10 || Math.sqrt(65)
    }

    def "getManhattanDistance"(int x1, int y1, int x2, int y2) {
        given:
        var c1 = new Coordinate(x1, y1)
        var c2 = new Coordinate(x2, y2)

        when:
        var manhattanDistance = c1.getManhattanDistance(c2)

        then:
        manhattanDistance == expected

        where:
        x1 | y1 | x2 | y2 || expected
        2  | 2  | 2  | 3  || 1
        2  | 2  | 2  | 7  || 5
        2  | 2  | 7  | 2  || 5
        2  | 2  | 6  | 6  || 8

    }

    def "toPosition"(int x, int y, int mapWidth, int mapHeight) {
        given:
        var c = new Coordinate(x, y)

        when:
        var arrayPosition = c.toArrayPosition(mapWidth, mapHeight)

        then:
        arrayPosition == expected

        where:
        mapWidth = 10
        mapHeight = 10
        x = random.nextInt(0, mapWidth -1)
        y = random.nextInt(0, mapHeight -1)
        expected = x + y * mapWidth
    }


    def "getDirection"(int x1, int y1, int x2, int y2) {
        given:
        var c1 = new Coordinate(x1, y1)
        var c2 = new Coordinate(x2, y2)

        when:
        var direction = c1.getDirection(c2)
        var reverseDirection  = c2.getDirection(c1)
        then:
        direction == expected
        reverseDirection == expectedReverse

        where:
        x1 | y1 | x2 | y2 || expected        | expectedReverse
        2  | 2  | 2  | 3  || Direction.UP    | Direction.DOWN
        2  | 2  | 2  | 1  || Direction.DOWN  | Direction.UP
        2  | 2  | 1  | 2  || Direction.LEFT  | Direction.RIGHT
        2  | 2  | 3  | 2  || Direction.RIGHT | Direction.LEFT
    }

    def "translateByDelta"(int x1, int y1, int dX, int dY) {
        given:
        var c = new Coordinate(x1,y1)

        when:
        var cTranslated = c.translateByDelta(dX,dY)

        then:
        cTranslated.x == x2
        cTranslated.y == y2

        where:
        x1 | y1 | dX | dY || x2      || y2
        1  | 1  | 5  | 0  || x1 + dX || y1 + dY
        1  | 1  | 0  | 5  || x1 + dX || y1 + dY
        1  | 1  | -5 | 0  || x1 + dX || y1 + dY
        1  | 1  | 0  | -5 || x1 + dX || y1 + dY
        1  | 1  | 5  | 5  || x1 + dX || y1 + dY
        1  | 1  | -5 | -5 || x1 + dX || y1 + dY

    }

    def "translateByDirection"(int x, int y, Direction direction) {
        given:
        var c = new Coordinate(x,y)

        when:
        var newCoor = c.translateByDirection(direction)

        then:
        newCoor.x == expected.x
        newCoor.y == expected.y

        where:
        x | y | direction       || expected
        2 | 2 | Direction.RIGHT || new Coordinate(x+1,y)
        2 | 2 | Direction.LEFT  || new Coordinate(x-1,y)
        2 | 2 | Direction.DOWN  || new Coordinate(x,y+1)
        2 | 2 | Direction.UP    || new Coordinate(x,y-1)
    }
}
