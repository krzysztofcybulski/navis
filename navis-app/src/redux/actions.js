export const loadShips = () => (dispatch) => fetch('http://localhost:8080/ships')
    .then(response => response.json())
    .then(ships => dispatch({ type: 'loadAllShips', payload: ships}))

export const updatePosition = ({ mmsi, longitude, latitude }) => ({
    type: 'updatePosition',
    payload: {
        mmsi,
        longitude,
        latitude
    }
})

export const updatePositions = (list) => ({
    type: 'updatePositions',
    payload: list
})

export const selectShip = ({ mmsi }) => ({
    type: 'selectShip',
    payload: { mmsi }
})
