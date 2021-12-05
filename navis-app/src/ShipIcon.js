import { Marker } from 'react-leaflet';
import L from 'leaflet';
import { connect } from 'react-redux';
import { selectShip } from './redux/actions';

const createIcon = (name, size) => L.icon({ iconUrl: `/ships/${name}.png`, iconSize: [size, size] });

const icons = ({
    unknown: createIcon('unknown', 32),
    cargo: createIcon('cargo', 32),
    fishing: createIcon('fishing', 32),
    sailing: createIcon('sailing', 32),
    passenger: createIcon('passenger', 32)
});

const selectedIcons = ({
    unknown: createIcon('unknown', 64),
    cargo: createIcon('cargo', 64),
    fishing: createIcon('fishing', 64),
    sailing: createIcon('sailing', 64),
    passenger: createIcon('passenger', 64)
});

const getIcon = (type) => icons[type.toLowerCase()] || icons['unknown'];

const getSelectedIcon = (type) => selectedIcons[type.toLowerCase()] || selectedIcons['unknown'];

const ShipIcon = ({ mmsi, latitude, longitude, type, select, selected }) =>
    <Marker position={[latitude || 0, longitude || 0]}
            icon={selected ? getSelectedIcon(type) : getIcon(type)}
            eventHandlers={{
                click: () => select({ mmsi })
            }}>
    </Marker>;

const mapStateToProps = (state, { mmsi }) => ({
    selected: state.selected && state.selected.mmsi === mmsi
});

const mapDispatchToProps = (dispatch) => ({
    select: (props) => dispatch(selectShip(props))
});

export default connect(mapStateToProps, mapDispatchToProps)(ShipIcon);
