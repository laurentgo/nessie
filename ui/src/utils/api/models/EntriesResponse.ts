/* tslint:disable */
/* eslint-disable */
/**
 * Nessie API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 0.8.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { exists, mapValues } from '../runtime';
import {
    Entry,
    EntryFromJSON,
    EntryFromJSONTyped,
    EntryToJSON,
} from './';

/**
 * 
 * @export
 * @interface EntriesResponse
 */
export interface EntriesResponse {
    /**
     * 
     * @type {string}
     * @memberof EntriesResponse
     */
    token?: string;
    /**
     * 
     * @type {Array<Entry>}
     * @memberof EntriesResponse
     */
    entries?: Array<Entry>;
}

export function EntriesResponseFromJSON(json: any): EntriesResponse {
    return EntriesResponseFromJSONTyped(json, false);
}

export function EntriesResponseFromJSONTyped(json: any, ignoreDiscriminator: boolean): EntriesResponse {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'token': !exists(json, 'token') ? undefined : json['token'],
        'entries': !exists(json, 'entries') ? undefined : ((json['entries'] as Array<any>).map(EntryFromJSON)),
    };
}

export function EntriesResponseToJSON(value?: EntriesResponse | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'token': value.token,
        'entries': value.entries === undefined ? undefined : ((value.entries as Array<any>).map(EntryToJSON)),
    };
}


