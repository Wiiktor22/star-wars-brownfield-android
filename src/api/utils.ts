const getIdFromTheUrl = (url: string): string | null => {
    if (!url.includes("/")) {
        return null;
    }
    const startIndex = url.lastIndexOf("/", url.length - 2) + 1;
    return url.substring(startIndex, url.length - 1);
}

export const getVehiclePhotoUri = (url: string): string | null => {
    const ID = getIdFromTheUrl(url);
    if (!ID) return null
    return `https://starwars-visualguide.com/assets/img/vehicles/${ID}.jpg`
}