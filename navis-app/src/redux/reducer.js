const initialState = {
    ships: {},
    selected: null
};

export default function appReducer(state = initialState, action) {
    switch (action.type) {
        case 'loadAllShips':
            return {
                ...state,
                ships: action.payload.reduce((acc, ship) => ({ ...acc, [ship.mmsi]: ship }), {})
            };
        case 'updatePosition':
            console.log(action.payload);
            const { mmsi, longitude, latitude } = action.payload;
            console.log(`Updating position of ${mmsi} to ${latitude} x ${longitude}`);
            return {
                ...state,
                ships: {
                    ...state.ships,
                    [mmsi]: {
                        ...state.ships[mmsi],
                        longitude,
                        latitude
                    }
                }
            };
        case 'updatePositions':
            return {
                ...state,
                ships: action.payload.reduce((ships, { mmsi, latitude, longitude }) => ({
                    ...ships,
                    [mmsi]: {
                        ...state.ships[mmsi],
                        latitude,
                        longitude
                    }
                }), state.ships)
            };
        case 'selectShip':
            return {
                ...state,
                selected: state.ships[action.payload.mmsi]
            };
        default:
            return state;
    }
}
