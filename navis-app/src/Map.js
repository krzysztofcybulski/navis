import { useEffect, useState } from 'react';
import { TileLayer, useMapEvents } from 'react-leaflet';
import { connect } from 'react-redux';
import ShipIcon from './ShipIcon';

const Map = ({ ships }) => {

    const [bounds, setBounds] = useState(null);
    const [moved, setMoved] = useState(false);
    const [visibleShips, setVisibleShips] = useState([]);

    const map = useMapEvents({
        moveend() {
            setBounds(map.getBounds());
        }
    });

    useEffect(() => {
        const shipsWithPositions = Object.entries(ships)
            .filter(([_, value]) => value.longitude)
            .map(([_, value]) => value);
        if (shipsWithPositions.length > 0 && map && !moved) {
            const randomShip = shipsWithPositions[shipsWithPositions.length * Math.random() << 0];
            map.flyTo([randomShip.latitude, randomShip.longitude]);
            setMoved(true);
        }
    }, [ships, map, moved]);

    useEffect(() => {
        if (map) {
            const { _northEast, _southWest } = map.getBounds();
            setVisibleShips(Object.values(ships)
                .filter(ship => ship.latitude && ship.longitude)
                .filter(ship => ship.latitude < _northEast.lat
                    && ship.latitude > _southWest.lat
                    && ship.longitude < _northEast.lng
                    && ship.longitude > _southWest.lng
                ));
        }
    }, [bounds]);

    return <>
        <TileLayer
            attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            maxZoom={12}
            minZoom={8}
        />
        {visibleShips.map(ship => <ShipIcon key={ship.mmsi}  {...ship} />)}
    </>;
};

const mapStateToProps = (state) => ({ ships: state.ships });

export default connect(mapStateToProps)(Map);
