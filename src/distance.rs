fn naturalDistance(u: i32, v: i32) -> f64 {
    let latitudeU = deg_to_rad(city1.getNodeInfo(u).latitude)
    let longitudeU = deg_to_rad(city1.getNodeInfo(u).longitude)
    let latitudeV = deg_to_rad(city2.getNodeInfo(v).latitude)
    let longitudeV = deg_to_rad(city2.getNodeInfo(v).longitude)

    let a = sin((latitudeV - latitudeU) / 2).pow(2) +
            cos(latitudeU) * cos(latitudeV) * sin((longitudeV - longitudeU) / 2).pow(2)

    let r = 6373000
    let c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return r * c
}