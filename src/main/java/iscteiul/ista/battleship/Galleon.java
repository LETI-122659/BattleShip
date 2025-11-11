/**
 *
 */
package iscteiul.ista.battleship;

public class Galleon extends Ship {
    private static final Integer SIZE = Integer.valueOf(5);
    private static final String NAME = "Galeao";

    public Galleon(Compass bearing, IPosition pos) throws IllegalArgumentException {
        super(Galleon.NAME, bearing, pos);

        if (bearing == null)
            throw new NullPointerException("ERROR! invalid bearing for the galleon");

        switch (bearing) {
            case NORTH:
                fillNorth(pos);
                break;
            case EAST:
                fillEast(pos);
                break;
            case SOUTH:
                fillSouth(pos);
                break;
            case WEST:
                fillWest(pos);
                break;
            default:
                throw new IllegalArgumentException("ERROR! invalid bearing for the galleon");
        }
    }

    @Override
    public Integer getSize() {
        return Galleon.SIZE;
    }

    private void fillNorth(IPosition pos) {
        for (int i = 0; i < getSize(); i++) {
            getPositions().add(new Position(pos.getRow() + i, pos.getColumn()));
        }
    }

    private void fillSouth(IPosition pos) {
        for (int i = 0; i < getSize(); i++) {
            getPositions().add(new Position(pos.getRow() - i, pos.getColumn()));
        }
    }

    private void fillEast(IPosition pos) {
        for (int i = 0; i < getSize(); i++) {
            getPositions().add(new Position(pos.getRow(), pos.getColumn() + i));
        }
    }

    private void fillWest(IPosition pos) {
        for (int i = 0; i < getSize(); i++) {
            getPositions().add(new Position(pos.getRow(), pos.getColumn() - i));
        }
    }
}
